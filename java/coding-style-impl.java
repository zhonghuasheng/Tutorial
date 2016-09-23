/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.impl;

/**
 * Provides the remote service for accessing, adding, authenticating, deleting,
 * and updating users. Its methods include permission checks.
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Scott Lee
 * @author Jorge Ferrer
 * @author Julio Camarero
 */
public class UserServiceImpl extends UserServiceBaseImpl {
// 1. 类下面的第一行空行
    /**
     * Adds the users to the group.
     *
     * @param  groupId the primary key of the group
     * @param  userIds the primary keys of the users
     * @param  serviceContext the service context to be applied (optionally
     *         <code>null</code>)
     * @throws PortalException if a group or user with the primary key could not
     *         be found, if the user did not have permission to assign group
     *         members, or if the operation was not allowed by the membership
     *         policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void addGroupUsers(long groupId, long[] userIds, ServiceContext serviceContext) throws PortalException, SystemException {
        if (userIds.length == 0) {
            return;
        }

        try {
            GroupPermissionUtil.check(getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);
        } catch (PrincipalException pe) {
            boolean hasPermission = false;
            // 2. if前空行
            if (userIds.length == 1) {
                User user = getUser();

                if (user.getUserId() == userIds[0]) {
                    Group group = groupPersistence.findByPrimaryKey(groupId);

                    if (user.getCompanyId() == group.getCompanyId()) {
                        int type = group.getType();

                        if (type == GroupConstants.TYPE_SITE_OPEN) {
                            hasPermission = true;
                        }
                    }
                }
            }

            if (!hasPermission) {
                throw new PrincipalException();
            }
        }

        SiteMembershipPolicyUtil.checkMembership(userIds, new long[] {groupId}, null);

        userLocalService.addGroupUsers(groupId, userIds);
        SiteMembershipPolicyUtil.propagateMembership(userIds, new long[] {groupId}, null);
    }

    /**
     * Adds the users to the organization.
     *
     * @param  organizationId the primary key of the organization
     * @param  userIds the primary keys of the users
     * @throws PortalException if an organization or user with the primary key
     *         could not be found, if the user did not have permission to assign
     *         organization members, if current user did not have an
     *         organization in common with a given user, or if the operation was
     *         not allowed by the membership policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void addOrganizationUsers(long organizationId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        OrganizationPermissionUtil.check(
            getPermissionChecker(), organizationId, ActionKeys.ASSIGN_MEMBERS);

        validateOrganizationUsers(userIds);

        OrganizationMembershipPolicyUtil.checkMembership(
            userIds, new long[] {organizationId}, null);

        userLocalService.addOrganizationUsers(organizationId, userIds);

        OrganizationMembershipPolicyUtil.propagateMembership(
            userIds, new long[] {organizationId}, null);
    }

    /**
     * Assigns the password policy to the users, removing any other currently
     * assigned password policies.
     *
     * @param  passwordPolicyId the primary key of the password policy
     * @param  userIds the primary keys of the users
     * @throws PortalException if the user did not have permission to assign
     *         policy members
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void addPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        PasswordPolicyPermissionUtil.check(
            getPermissionChecker(), passwordPolicyId,
            ActionKeys.ASSIGN_MEMBERS);

        userLocalService.addPasswordPolicyUsers(passwordPolicyId, userIds);
    }

    /**
     * Adds the users to the role.
     *
     * @param  roleId the primary key of the role
     * @param  userIds the primary keys of the users
     * @throws PortalException if a role or user with the primary key could not
     *         be found, if the user did not have permission to assign role
     *         members, or if the operation was not allowed by the membership
     *         policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void addRoleUsers(long roleId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        RolePermissionUtil.check(
            getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

        RoleMembershipPolicyUtil.checkRoles(userIds, new long[] {roleId}, null);

        userLocalService.addRoleUsers(roleId, userIds);

        RoleMembershipPolicyUtil.propagateRoles(
            userIds, new long[] {roleId}, null);
    }

    /**
     * Adds the users to the team.
     *
     * @param  teamId the primary key of the team
     * @param  userIds the primary keys of the users
     * @throws PortalException if a team or user with the primary key could not
     *         be found or if the user did not have permission to assign team
     *         members
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void addTeamUsers(long teamId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        TeamPermissionUtil.check(
            getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

        userLocalService.addTeamUsers(teamId, userIds);
    }

    /**
     * Adds a user.
     *
     * <p>
     * This method handles the creation and bookkeeping of the user including
     * its resources, metadata, and internal data structures. It is not
     * necessary to make subsequent calls to any methods to setup default
     * groups, resources, etc.
     * </p>
     *
     * @param  companyId the primary key of the user's company
     * @param  autoPassword whether a password should be automatically generated
     *         for the user
     * @param  password1 the user's password
     * @param  password2 the user's password confirmation
     * @param  autoScreenName whether a screen name should be automatically
     *         generated for the user
     * @param  screenName the user's screen name
     * @param  emailAddress the user's email address
     * @param  facebookId the user's facebook ID
     * @param  openId the user's OpenID
     * @param  locale the user's locale
     * @param  firstName the user's first name
     * @param  middleName the user's middle name
     * @param  lastName the user's last name
     * @param  prefixId the user's name prefix ID
     * @param  suffixId the user's name suffix ID
     * @param  male whether the user is male
     * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
     *         January)
     * @param  birthdayDay the user's birthday day
     * @param  birthdayYear the user's birthday year
     * @param  jobTitle the user's job title
     * @param  groupIds the primary keys of the user's groups
     * @param  organizationIds the primary keys of the user's organizations
     * @param  roleIds the primary keys of the roles this user possesses
     * @param  userGroupIds the primary keys of the user's user groups
     * @param  sendEmail whether to send the user an email notification about
     *         their new account
     * @param  serviceContext the service context to be applied (optionally
     *         <code>null</code>). Can set the UUID (with the <code>uuid</code>
     *         attribute), asset category IDs, asset tag names, and expando
     *         bridge attributes for the user.
     * @return the new user
     * @throws PortalException if the user's information was invalid, if the
     *         operation was not allowed by the membership policy, if the
     *         creator did not have permission to add users, or if the email
     *         address was reserved
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User addUser(
            long companyId, boolean autoPassword, String password1,
            String password2, boolean autoScreenName, String screenName,
            String emailAddress, long facebookId, String openId, Locale locale,
            String firstName, String middleName, String lastName, int prefixId,
            int suffixId, boolean male, int birthdayMonth, int birthdayDay,
            int birthdayYear, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds, long[] userGroupIds,
            boolean sendEmail, ServiceContext serviceContext)
        throws PortalException, SystemException {

        boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

        try {
            WorkflowThreadLocal.setEnabled(false);

            return addUserWithWorkflow(
                companyId, autoPassword, password1, password2, autoScreenName,
                screenName, emailAddress, facebookId, openId, locale, firstName,
                middleName, lastName, prefixId, suffixId, male, birthdayMonth,
                birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
                roleIds, userGroupIds, sendEmail, serviceContext);
        }
        finally {
            WorkflowThreadLocal.setEnabled(workflowEnabled);
        }
    }

    /**
     * Adds a user with additional parameters.
     *
     * <p>
     * This method handles the creation and bookkeeping of the user including
     * its resources, metadata, and internal data structures. It is not
     * necessary to make subsequent calls to any methods to setup default
     * groups, resources, etc.
     * </p>
     *
     * @param  companyId the primary key of the user's company
     * @param  autoPassword whether a password should be automatically generated
     *         for the user
     * @param  password1 the user's password
     * @param  password2 the user's password confirmation
     * @param  autoScreenName whether a screen name should be automatically
     *         generated for the user
     * @param  screenName the user's screen name
     * @param  emailAddress the user's email address
     * @param  facebookId the user's facebook ID
     * @param  openId the user's OpenID
     * @param  locale the user's locale
     * @param  firstName the user's first name
     * @param  middleName the user's middle name
     * @param  lastName the user's last name
     * @param  prefixId the user's name prefix ID
     * @param  suffixId the user's name suffix ID
     * @param  male whether the user is male
     * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
     *         January)
     * @param  birthdayDay the user's birthday day
     * @param  birthdayYear the user's birthday year
     * @param  jobTitle the user's job title
     * @param  groupIds the primary keys of the user's groups
     * @param  organizationIds the primary keys of the user's organizations
     * @param  roleIds the primary keys of the roles this user possesses
     * @param  userGroupIds the primary keys of the user's user groups
     * @param  addresses the user's addresses
     * @param  emailAddresses the user's email addresses
     * @param  phones the user's phone numbers
     * @param  websites the user's websites
     * @param  announcementsDelivers the announcements deliveries
     * @param  sendEmail whether to send the user an email notification about
     *         their new account
     * @param  serviceContext the service context to be applied (optionally
     *         <code>null</code>). Can set the UUID (with the <code>uuid</code>
     *         attribute), asset category IDs, asset tag names, and expando
     *         bridge attributes for the user.
     * @return the new user
     * @throws PortalException if the user's information was invalid, if the
     *         creator did not have permission to add users, if the email
     *         address was reserved, if the operation was not allowed by the
     *         membership policy, or if some other portal exception occurred
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User addUser(
            long companyId, boolean autoPassword, String password1,
            String password2, boolean autoScreenName, String screenName,
            String emailAddress, long facebookId, String openId, Locale locale,
            String firstName, String middleName, String lastName, int prefixId,
            int suffixId, boolean male, int birthdayMonth, int birthdayDay,
            int birthdayYear, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds, long[] userGroupIds,
            List<Address> addresses, List<EmailAddress> emailAddresses,
            List<Phone> phones, List<Website> websites,
            List<AnnouncementsDelivery> announcementsDelivers,
            boolean sendEmail, ServiceContext serviceContext)
        throws PortalException, SystemException {

        boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

        try {
            WorkflowThreadLocal.setEnabled(false);

            return addUserWithWorkflow(
                companyId, autoPassword, password1, password2, autoScreenName,
                screenName, emailAddress, facebookId, openId, locale, firstName,
                middleName, lastName, prefixId, suffixId, male, birthdayMonth,
                birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
                roleIds, userGroupIds, addresses, emailAddresses, phones,
                websites, announcementsDelivers, sendEmail, serviceContext);
        }
        finally {
            WorkflowThreadLocal.setEnabled(workflowEnabled);
        }
    }

    /**
     * Adds the users to the user group.
     *
     * @param  userGroupId the primary key of the user group
     * @param  userIds the primary keys of the users
     * @throws PortalException if a user group or user with the primary could
     *         could not be found, if the current user did not have permission
     *         to assign group members, or if the operation was not allowed by
     *         the membership policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void addUserGroupUsers(long userGroupId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        UserGroupPermissionUtil.check(
            getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_MEMBERS);

        UserGroupMembershipPolicyUtil.checkMembership(
            userIds, new long[] {userGroupId}, null);

        userLocalService.addUserGroupUsers(userGroupId, userIds);

        UserGroupMembershipPolicyUtil.propagateMembership(
            userIds, new long[] {userGroupId}, null);
    }

    /**
     * Adds a user with workflow.
     *
     * <p>
     * This method handles the creation and bookkeeping of the user including
     * its resources, metadata, and internal data structures. It is not
     * necessary to make subsequent calls to any methods to setup default
     * groups, resources, etc.
     * </p>
     *
     * @param  companyId the primary key of the user's company
     * @param  autoPassword whether a password should be automatically generated
     *         for the user
     * @param  password1 the user's password
     * @param  password2 the user's password confirmation
     * @param  autoScreenName whether a screen name should be automatically
     *         generated for the user
     * @param  screenName the user's screen name
     * @param  emailAddress the user's email address
     * @param  facebookId the user's facebook ID
     * @param  openId the user's OpenID
     * @param  locale the user's locale
     * @param  firstName the user's first name
     * @param  middleName the user's middle name
     * @param  lastName the user's last name
     * @param  prefixId the user's name prefix ID
     * @param  suffixId the user's name suffix ID
     * @param  male whether the user is male
     * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
     *         January)
     * @param  birthdayDay the user's birthday day
     * @param  birthdayYear the user's birthday year
     * @param  jobTitle the user's job title
     * @param  groupIds the primary keys of the user's groups
     * @param  organizationIds the primary keys of the user's organizations
     * @param  roleIds the primary keys of the roles this user possesses
     * @param  userGroupIds the primary keys of the user's user groups
     * @param  sendEmail whether to send the user an email notification about
     *         their new account
     * @param  serviceContext the service context to be applied (optionally
     *         <code>null</code>). Can set the UUID (with the <code>uuid</code>
     *         attribute), asset category IDs, asset tag names, and expando
     *         bridge attributes for the user.
     * @return the new user
     * @throws PortalException if the user's information was invalid, if the
     *         operation was not allowed by the membership policy, if the
     *         creator did not have permission to add users, or if the email
     *         address was reserved
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User addUserWithWorkflow(
            long companyId, boolean autoPassword, String password1,
            String password2, boolean autoScreenName, String screenName,
            String emailAddress, long facebookId, String openId, Locale locale,
            String firstName, String middleName, String lastName, int prefixId,
            int suffixId, boolean male, int birthdayMonth, int birthdayDay,
            int birthdayYear, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds, long[] userGroupIds,
            boolean sendEmail, ServiceContext serviceContext)
        throws PortalException, SystemException {

        long creatorUserId = 0;

        try {
            creatorUserId = getGuestOrUserId();
        }
        catch (PrincipalException pe) {
        }

        checkAddUserPermission(
            creatorUserId, companyId, emailAddress, groupIds, organizationIds,
            roleIds, userGroupIds, serviceContext);

        User user = userLocalService.addUserWithWorkflow(
            creatorUserId, companyId, autoPassword, password1, password2,
            autoScreenName, screenName, emailAddress, facebookId, openId,
            locale, firstName, middleName, lastName, prefixId, suffixId, male,
            birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
            organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

        checkMembership(
            new long[] {user.getUserId()}, groupIds, organizationIds, roleIds,
            userGroupIds);

        propagateMembership(
            new long[] {user.getUserId()}, groupIds, organizationIds, roleIds,
            userGroupIds);

        return user;
    }

    /**
     * Adds a user with workflow and additional parameters.
     *
     * <p>
     * This method handles the creation and bookkeeping of the user including
     * its resources, metadata, and internal data structures. It is not
     * necessary to make subsequent calls to any methods to setup default
     * groups, resources, etc.
     * </p>
     *
     * @param  companyId the primary key of the user's company
     * @param  autoPassword whether a password should be automatically generated
     *         for the user
     * @param  password1 the user's password
     * @param  password2 the user's password confirmation
     * @param  autoScreenName whether a screen name should be automatically
     *         generated for the user
     * @param  screenName the user's screen name
     * @param  emailAddress the user's email address
     * @param  facebookId the user's facebook ID
     * @param  openId the user's OpenID
     * @param  locale the user's locale
     * @param  firstName the user's first name
     * @param  middleName the user's middle name
     * @param  lastName the user's last name
     * @param  prefixId the user's name prefix ID
     * @param  suffixId the user's name suffix ID
     * @param  male whether the user is male
     * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
     *         January)
     * @param  birthdayDay the user's birthday day
     * @param  birthdayYear the user's birthday year
     * @param  jobTitle the user's job title
     * @param  groupIds the primary keys of the user's groups
     * @param  organizationIds the primary keys of the user's organizations
     * @param  roleIds the primary keys of the roles this user possesses
     * @param  userGroupIds the primary keys of the user's user groups
     * @param  addresses the user's addresses
     * @param  emailAddresses the user's email addresses
     * @param  phones the user's phone numbers
     * @param  websites the user's websites
     * @param  announcementsDelivers the announcements deliveries
     * @param  sendEmail whether to send the user an email notification about
     *         their new account
     * @param  serviceContext the service context to be applied (optionally
     *         <code>null</code>). Can set the UUID (with the <code>uuid</code>
     *         attribute), asset category IDs, asset tag names, and expando
     *         bridge attributes for the user.
     * @return the new user
     * @throws PortalException if the user's information was invalid, if the
     *         operation was not allowed by the membership policy, if the
     *         creator did not have permission to add users, if the email
     *         address was reserved, or if some other portal exception occurred
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User addUserWithWorkflow(
            long companyId, boolean autoPassword, String password1,
            String password2, boolean autoScreenName, String screenName,
            String emailAddress, long facebookId, String openId, Locale locale,
            String firstName, String middleName, String lastName, int prefixId,
            int suffixId, boolean male, int birthdayMonth, int birthdayDay,
            int birthdayYear, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds, long[] userGroupIds,
            List<Address> addresses, List<EmailAddress> emailAddresses,
            List<Phone> phones, List<Website> websites,
            List<AnnouncementsDelivery> announcementsDelivers,
            boolean sendEmail, ServiceContext serviceContext)
        throws PortalException, SystemException {

        boolean indexingEnabled = true;

        if (serviceContext != null) {
            indexingEnabled = serviceContext.isIndexingEnabled();

            serviceContext.setIndexingEnabled(false);
        }

        try {
            User user = addUserWithWorkflow(
                companyId, autoPassword, password1, password2, autoScreenName,
                screenName, emailAddress, facebookId, openId, locale, firstName,
                middleName, lastName, prefixId, suffixId, male, birthdayMonth,
                birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
                roleIds, userGroupIds, sendEmail, serviceContext);

            UsersAdminUtil.updateAddresses(
                Contact.class.getName(), user.getContactId(), addresses);

            UsersAdminUtil.updateEmailAddresses(
                Contact.class.getName(), user.getContactId(), emailAddresses);

            UsersAdminUtil.updatePhones(
                Contact.class.getName(), user.getContactId(), phones);

            UsersAdminUtil.updateWebsites(
                Contact.class.getName(), user.getContactId(), websites);

            updateAnnouncementsDeliveries(
                user.getUserId(), announcementsDelivers);

            if (indexingEnabled) {
                Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
                    User.class);

                indexer.reindex(user);
            }

            return user;
        }
        finally {
            if (serviceContext != null) {
                serviceContext.setIndexingEnabled(indexingEnabled);
            }
        }
    }

    /**
     * Deletes the user's portrait image.
     *
     * @param  userId the primary key of the user
     * @throws PortalException if a user with the primary key could not be
     *         found, if the user's portrait could not be found, or if the
     *         current user did not have permission to update the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void deletePortrait(long userId)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.UPDATE);

        userLocalService.deletePortrait(userId);
    }

    /**
     * Removes the user from the role.
     *
     * @param  roleId the primary key of the role
     * @param  userId the primary key of the user
     * @throws PortalException if a role or user with the primary key could not
     *         be found, or if the current user did not have permission to
     *         assign role members
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void deleteRoleUser(long roleId, long userId)
        throws PortalException, SystemException {

        RolePermissionUtil.check(
            getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

        userLocalService.deleteRoleUser(roleId, userId);
    }

    /**
     * Deletes the user.
     *
     * @param  userId the primary key of the user
     * @throws PortalException if a user with the primary key could not be found
     *         or if the current user did not have permission to delete the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void deleteUser(long userId)
        throws PortalException, SystemException {

        if (getUserId() == userId) {
            throw new RequiredUserException();
        }

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.DELETE);

        userLocalService.deleteUser(userId);
    }

    @Override
    public List<User> getCompanyUsers(long companyId, int start, int end)
        throws PortalException, SystemException {

        PermissionChecker permissionChecker = getPermissionChecker();

        if (!permissionChecker.isCompanyAdmin(companyId)) {
            throw new PrincipalException();
        }

        return userPersistence.findByCompanyId(companyId, start, end);
    }

    @Override
    public int getCompanyUsersCount(long companyId)
        throws PortalException, SystemException {

        PermissionChecker permissionChecker = getPermissionChecker();

        if (!permissionChecker.isCompanyAdmin(companyId)) {
            throw new PrincipalException();
        }

        return userPersistence.countByCompanyId(companyId);
    }

    @Override
    public User getCurrentUser() throws PortalException, SystemException {
        return getUser();
    }

    /**
     * Returns the primary keys of all the users belonging to the group.
     *
     * @param  groupId the primary key of the group
     * @return the primary keys of the users belonging to the group
     * @throws PortalException if the current user did not have permission to
     *         view group assignments
     * @throws SystemException if a system exception occurred
     */
    @Override
    public long[] getGroupUserIds(long groupId)
        throws PortalException, SystemException {

        GroupPermissionUtil.check(
            getPermissionChecker(), groupId, ActionKeys.VIEW_MEMBERS);

        return userLocalService.getGroupUserIds(groupId);
    }

    /**
     * Returns all the users belonging to the group.
     *
     * @param  groupId the primary key of the group
     * @return the users belonging to the group
     * @throws PortalException if the current user did not have permission to
     *         view group assignments
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<User> getGroupUsers(long groupId)
        throws PortalException, SystemException {

        GroupPermissionUtil.check(
            getPermissionChecker(), groupId, ActionKeys.VIEW_MEMBERS);

        return userLocalService.getGroupUsers(groupId);
    }

    /**
     * Returns the primary keys of all the users belonging to the organization.
     *
     * @param  organizationId the primary key of the organization
     * @return the primary keys of the users belonging to the organization
     * @throws PortalException if the current user did not have permission to
     *         view organization assignments
     * @throws SystemException if a system exception occurred
     */
    @Override
    public long[] getOrganizationUserIds(long organizationId)
        throws PortalException, SystemException {

        OrganizationPermissionUtil.check(
            getPermissionChecker(), organizationId, ActionKeys.VIEW_MEMBERS);

        return userLocalService.getOrganizationUserIds(organizationId);
    }

    /**
     * Returns all the users belonging to the organization.
     *
     * @param  organizationId the primary key of the organization
     * @return users belonging to the organization
     * @throws PortalException if the current user did not have permission to
     *         view organization assignments
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<User> getOrganizationUsers(long organizationId)
        throws PortalException, SystemException {

        OrganizationPermissionUtil.check(
            getPermissionChecker(), organizationId, ActionKeys.VIEW_MEMBERS);

        return userLocalService.getOrganizationUsers(organizationId);
    }

    /**
     * Returns the primary keys of all the users belonging to the role.
     *
     * @param  roleId the primary key of the role
     * @return the primary keys of the users belonging to the role
     * @throws PortalException if the current user did not have permission to
     *         view role members
     * @throws SystemException if a system exception occurred
     */
    @Override
    public long[] getRoleUserIds(long roleId)
        throws PortalException, SystemException {

        RolePermissionUtil.check(
            getPermissionChecker(), roleId, ActionKeys.VIEW);

        return userLocalService.getRoleUserIds(roleId);
    }

    /**
     * Returns the user with the email address.
     *
     * @param  companyId the primary key of the user's company
     * @param  emailAddress the user's email address
     * @return the user with the email address
     * @throws PortalException if a user with the email address could not be
     *         found or if the current user did not have permission to view the
     *         user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User getUserByEmailAddress(long companyId, String emailAddress)
        throws PortalException, SystemException {

        User user = userLocalService.getUserByEmailAddress(
            companyId, emailAddress);

        UserPermissionUtil.check(
            getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

        return user;
    }

    /**
     * Returns the user with the primary key.
     *
     * @param  userId the primary key of the user
     * @return the user with the primary key
     * @throws PortalException if a user with the primary key could not be found
     *         or if the current user did not have permission to view the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User getUserById(long userId)
        throws PortalException, SystemException {

        User user = userPersistence.findByPrimaryKey(userId);

        UserPermissionUtil.check(
            getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

        return user;
    }

    /**
     * Returns the user with the screen name.
     *
     * @param  companyId the primary key of the user's company
     * @param  screenName the user's screen name
     * @return the user with the screen name
     * @throws PortalException if a user with the screen name could not be found
     *         or if the current user did not have permission to view the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User getUserByScreenName(long companyId, String screenName)
        throws PortalException, SystemException {

        User user = userLocalService.getUserByScreenName(companyId, screenName);

        UserPermissionUtil.check(
            getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

        return user;
    }

    @Override
    public List<User> getUserGroupUsers(long userGroupId)
        throws PortalException, SystemException {

        UserGroupPermissionUtil.check(
            getPermissionChecker(), userGroupId, ActionKeys.VIEW_MEMBERS);

        return userGroupPersistence.getUsers(userGroupId);
    }

    /**
     * Returns the primary key of the user with the email address.
     *
     * @param  companyId the primary key of the user's company
     * @param  emailAddress the user's email address
     * @return the primary key of the user with the email address
     * @throws PortalException if a user with the email address could not be
     *         found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public long getUserIdByEmailAddress(long companyId, String emailAddress)
        throws PortalException, SystemException {

        User user = getUserByEmailAddress(companyId, emailAddress);

        UserPermissionUtil.check(
            getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

        return user.getUserId();
    }

    /**
     * Returns the primary key of the user with the screen name.
     *
     * @param  companyId the primary key of the user's company
     * @param  screenName the user's screen name
     * @return the primary key of the user with the screen name
     * @throws PortalException if a user with the screen name could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public long getUserIdByScreenName(long companyId, String screenName)
        throws PortalException, SystemException {

        User user = getUserByScreenName(companyId, screenName);

        UserPermissionUtil.check(
            getPermissionChecker(), user.getUserId(), ActionKeys.VIEW);

        return user.getUserId();
    }

    /**
     * Returns <code>true</code> if the user is a member of the group.
     *
     * @param  groupId the primary key of the group
     * @param  userId the primary key of the user
     * @return <code>true</code> if the user is a member of the group;
     *         <code>false</code> otherwise
     * @throws PortalException if the current user did not have permission to
     *         view the user or group members
     * @throws SystemException if a system exception occurred
     */
    @Override
    public boolean hasGroupUser(long groupId, long userId)
        throws PortalException, SystemException {

        try {
            UserPermissionUtil.check(
                getPermissionChecker(), userId, ActionKeys.VIEW);
        }
        catch (PrincipalException pe) {
            GroupPermissionUtil.check(
                getPermissionChecker(), groupId, ActionKeys.VIEW_MEMBERS);
        }

        return userLocalService.hasGroupUser(groupId, userId);
    }

    /**
     * Returns <code>true</code> if the user is a member of the role.
     *
     * @param  roleId the primary key of the role
     * @param  userId the primary key of the user
     * @return <code>true</code> if the user is a member of the role;
     *         <code>false</code> otherwise
     * @throws PortalException if the current user did not have permission to
     *         view the user or role members
     * @throws SystemException if a system exception occurred
     */
    @Override
    public boolean hasRoleUser(long roleId, long userId)
        throws PortalException, SystemException {

        try {
            UserPermissionUtil.check(
                getPermissionChecker(), userId, ActionKeys.VIEW);
        }
        catch (PrincipalException pe) {
            RolePermissionUtil.check(
                getPermissionChecker(), roleId, ActionKeys.VIEW_MEMBERS);
        }

        return userLocalService.hasRoleUser(roleId, userId);
    }

    /**
     * Returns <code>true</code> if the user has the role with the name,
     * optionally through inheritance.
     *
     * @param  companyId the primary key of the role's company
     * @param  name the name of the role (must be a regular role, not an
     *         organization, site or provider role)
     * @param  userId the primary key of the user
     * @param  inherited whether to include roles inherited from organizations,
     *         sites, etc.
     * @return <code>true</code> if the user has the role; <code>false</code>
     *         otherwise
     * @throws PortalException if a role with the name could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public boolean hasRoleUser(
            long companyId, String name, long userId, boolean inherited)
        throws PortalException, SystemException {

        try {
            UserPermissionUtil.check(
                getPermissionChecker(), userId, ActionKeys.VIEW);
        }
        catch (PrincipalException pe) {
            Role role = roleLocalService.getRole(companyId, name);

            RolePermissionUtil.check(
                getPermissionChecker(), role.getRoleId(),
                ActionKeys.VIEW_MEMBERS);
        }

        return userLocalService.hasRoleUser(companyId, name, userId, inherited);
    }

    /**
     * Sets the users in the role, removing and adding users to the role as
     * necessary.
     *
     * @param  roleId the primary key of the role
     * @param  userIds the primary keys of the users
     * @throws PortalException if the current user did not have permission to
     *         assign role members or if the operation was not allowed by the
     *         membership policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void setRoleUsers(long roleId, long[] userIds)
        throws PortalException, SystemException {

        RolePermissionUtil.check(
            getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

        List<Long> unsetUserIds = new ArrayList<Long>(userIds.length);

        List<User> users = rolePersistence.getUsers(roleId);

        for (User user : users) {
            if (!ArrayUtil.contains(userIds, user.getUserId())) {
                unsetUserIds.add(user.getUserId());
            }
        }

        if (!unsetUserIds.isEmpty()) {
            RoleMembershipPolicyUtil.checkRoles(
                ArrayUtil.toLongArray(unsetUserIds), null, new long[] {roleId});
        }

        if (userIds.length > 0) {
            RoleMembershipPolicyUtil.checkRoles(
                userIds, new long[] {roleId}, null);
        }

        userLocalService.setRoleUsers(roleId, userIds);

        if (!unsetUserIds.isEmpty()) {
            RoleMembershipPolicyUtil.propagateRoles(
                ArrayUtil.toLongArray(unsetUserIds), null, new long[] {roleId});
        }

        if (userIds.length > 0) {
            RoleMembershipPolicyUtil.propagateRoles(
                userIds, new long[] {roleId}, null);
        }
    }

    /**
     * Sets the users in the user group, removing and adding users to the user
     * group as necessary.
     *
     * @param  userGroupId the primary key of the user group
     * @param  userIds the primary keys of the users
     * @throws PortalException if the current user did not have permission to
     *         assign group members
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void setUserGroupUsers(long userGroupId, long[] userIds)
        throws PortalException, SystemException {

        UserGroupPermissionUtil.check(
            getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_MEMBERS);

        List<Long> unsetUserIds = new ArrayList<Long>(userIds.length);

        List<User> users = userGroupPersistence.getUsers(userGroupId);

        for (User user : users) {
            if (!ArrayUtil.contains(userIds, user.getUserId())) {
                unsetUserIds.add(user.getUserId());
            }
        }

        if (!unsetUserIds.isEmpty()) {
            UserGroupMembershipPolicyUtil.checkMembership(
                ArrayUtil.toLongArray(unsetUserIds), null,
                new long[] {userGroupId});
        }

        if (userIds.length > 0) {
            UserGroupMembershipPolicyUtil.checkMembership(
                userIds, new long[] {userGroupId}, null);
        }

        userLocalService.setUserGroupUsers(userGroupId, userIds);

        if (!unsetUserIds.isEmpty()) {
            UserGroupMembershipPolicyUtil.propagateMembership(
                ArrayUtil.toLongArray(unsetUserIds), null,
                new long[] {userGroupId});
        }

        if (userIds.length > 0) {
            UserGroupMembershipPolicyUtil.propagateMembership(
                userIds, new long[] {userGroupId}, null);
        }
    }

    /**
     * Removes the users from the teams of a group.
     *
     * @param  groupId the primary key of the group
     * @param  userIds the primary keys of the users
     * @throws PortalException if the current user did not have permission to
     *         modify user group assignments
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void unsetGroupTeamsUsers(long groupId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        UserGroupPermissionUtil.check(
            getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);

        userLocalService.unsetGroupTeamsUsers(groupId, userIds);
    }

    /**
     * Removes the users from the group.
     *
     * @param  groupId the primary key of the group
     * @param  userIds the primary keys of the users
     * @param  serviceContext the service context to be applied (optionally
     *         <code>null</code>)
     * @throws PortalException if the current user did not have permission to
     *         modify group assignments or if the operation was not allowed by
     *         the membership policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void unsetGroupUsers(
            long groupId, long[] userIds, ServiceContext serviceContext)
        throws PortalException, SystemException {

        userIds = UsersAdminUtil.filterUnsetGroupUserIds(
            getPermissionChecker(), groupId, userIds);

        if (userIds.length == 0) {
            return;
        }

        try {
            GroupPermissionUtil.check(
                getPermissionChecker(), groupId, ActionKeys.ASSIGN_MEMBERS);
        }
        catch (PrincipalException pe) {

            // Allow any user to leave open and restricted sites

            boolean hasPermission = false;

            if (userIds.length == 1) {
                User user = getUser();

                if (user.getUserId() == userIds[0]) {
                    Group group = groupPersistence.findByPrimaryKey(groupId);

                    if (user.getCompanyId() == group.getCompanyId()) {
                        int type = group.getType();

                        if ((type == GroupConstants.TYPE_SITE_OPEN) ||
                            (type == GroupConstants.TYPE_SITE_RESTRICTED)) {

                            hasPermission = true;
                        }
                    }
                }
            }

            if (!hasPermission) {
                throw new PrincipalException();
            }
        }

        SiteMembershipPolicyUtil.checkMembership(
            userIds, null, new long[] {groupId});

        userLocalService.unsetGroupUsers(groupId, userIds, serviceContext);

        SiteMembershipPolicyUtil.propagateMembership(
            userIds, null, new long[] {groupId});
    }

    /**
     * Removes the users from the organization.
     *
     * @param  organizationId the primary key of the organization
     * @param  userIds the primary keys of the users
     * @throws PortalException if the current user did not have permission to
     *         modify organization assignments or if the operation was not
     *         allowed by the membership policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void unsetOrganizationUsers(long organizationId, long[] userIds)
        throws PortalException, SystemException {

        userIds = UsersAdminUtil.filterUnsetOrganizationUserIds(
            getPermissionChecker(), organizationId, userIds);

        if (userIds.length == 0) {
            return;
        }

        OrganizationPermissionUtil.check(
            getPermissionChecker(), organizationId, ActionKeys.ASSIGN_MEMBERS);

        OrganizationMembershipPolicyUtil.checkMembership(
            userIds, null, new long[] {organizationId});

        userLocalService.unsetOrganizationUsers(organizationId, userIds);

        OrganizationMembershipPolicyUtil.propagateMembership(
            userIds, null, new long[] {organizationId});
    }

    /**
     * Removes the users from the password policy.
     *
     * @param  passwordPolicyId the primary key of the password policy
     * @param  userIds the primary keys of the users
     * @throws PortalException if the current user did not have permission to
     *         modify policy assignments
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void unsetPasswordPolicyUsers(long passwordPolicyId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        PasswordPolicyPermissionUtil.check(
            getPermissionChecker(), passwordPolicyId,
            ActionKeys.ASSIGN_MEMBERS);

        userLocalService.unsetPasswordPolicyUsers(passwordPolicyId, userIds);
    }

    /**
     * Removes the users from the role.
     *
     * @param  roleId the primary key of the role
     * @param  userIds the primary keys of the users
     * @throws PortalException if the current user did not have permission to
     *         modify role assignments or if the operation was not allowed by
     *         the membership policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void unsetRoleUsers(long roleId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        RolePermissionUtil.check(
            getPermissionChecker(), roleId, ActionKeys.ASSIGN_MEMBERS);

        RoleMembershipPolicyUtil.checkRoles(userIds, null, new long[] {roleId});

        userLocalService.unsetRoleUsers(roleId, userIds);

        RoleMembershipPolicyUtil.propagateRoles(
            userIds, null, new long[] {roleId});
    }

    /**
     * Removes the users from the team.
     *
     * @param  teamId the primary key of the team
     * @param  userIds the primary keys of the users
     * @throws PortalException if the current user did not have permission to
     *         modify team assignments
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void unsetTeamUsers(long teamId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        TeamPermissionUtil.check(
            getPermissionChecker(), teamId, ActionKeys.ASSIGN_MEMBERS);

        userLocalService.unsetTeamUsers(teamId, userIds);
    }

    /**
     * Removes the users from the user group.
     *
     * @param  userGroupId the primary key of the user group
     * @param  userIds the primary keys of the users
     * @throws PortalException if the current user did not have permission to
     *         modify user group assignments or if the operation was not allowed
     *         by the membership policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void unsetUserGroupUsers(long userGroupId, long[] userIds)
        throws PortalException, SystemException {

        if (userIds.length == 0) {
            return;
        }

        UserGroupPermissionUtil.check(
            getPermissionChecker(), userGroupId, ActionKeys.ASSIGN_MEMBERS);

        UserGroupMembershipPolicyUtil.checkMembership(
            userIds, null, new long[] {userGroupId});

        userLocalService.unsetUserGroupUsers(userGroupId, userIds);

        UserGroupMembershipPolicyUtil.propagateMembership(
            userIds, null, new long[] {userGroupId});
    }

    /**
     * Updates the user's response to the terms of use agreement.
     *
     * @param  userId the primary key of the user
     * @param  agreedToTermsOfUse whether the user has agree to the terms of use
     * @return the user
     * @throws PortalException if the current user did not have permission to
     *         update the user's agreement to terms-of-use
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateAgreedToTermsOfUse(
            long userId, boolean agreedToTermsOfUse)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.UPDATE);

        return userLocalService.updateAgreedToTermsOfUse(
            userId, agreedToTermsOfUse);
    }

    /**
     * Updates the user's email address.
     *
     * @param  userId the primary key of the user
     * @param  password the user's password
     * @param  emailAddress1 the user's new email address
     * @param  emailAddress2 the user's new email address confirmation
     * @param  serviceContext the service context to be applied. Must set the
     *         portal URL, main path, primary key of the layout, remote address,
     *         remote host, and agent for the user.
     * @return the user
     * @throws PortalException if a user with the primary key could not be found
     *         or if the current user did not have permission to update the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateEmailAddress(
            long userId, String password, String emailAddress1,
            String emailAddress2, ServiceContext serviceContext)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.UPDATE);

        User user = userPersistence.findByPrimaryKey(userId);

        validateEmailAddress(user, emailAddress2);

        return userLocalService.updateEmailAddress(
            userId, password, emailAddress1, emailAddress2, serviceContext);
    }

    /**
     * Updates a user account that was automatically created when a guest user
     * participated in an action (e.g. posting a comment) and only provided his
     * name and email address.
     *
     * @param  companyId the primary key of the user's company
     * @param  autoPassword whether a password should be automatically generated
     *         for the user
     * @param  password1 the user's password
     * @param  password2 the user's password confirmation
     * @param  autoScreenName whether a screen name should be automatically
     *         generated for the user
     * @param  screenName the user's screen name
     * @param  emailAddress the user's email address
     * @param  facebookId the user's facebook ID
     * @param  openId the user's OpenID
     * @param  locale the user's locale
     * @param  firstName the user's first name
     * @param  middleName the user's middle name
     * @param  lastName the user's last name
     * @param  prefixId the user's name prefix ID
     * @param  suffixId the user's name suffix ID
     * @param  male whether the user is male
     * @param  birthdayMonth the user's birthday month (0-based, meaning 0 for
     *         January)
     * @param  birthdayDay the user's birthday day
     * @param  birthdayYear the user's birthday year
     * @param  jobTitle the user's job title
     * @param  updateUserInformation whether to update the user's information
     * @param  sendEmail whether to send the user an email notification about
     *         their new account
     * @param  serviceContext the service context to be applied (optionally
     *         <code>null</code>). Can set the expando bridge attributes for the
     *         user.
     * @return the user
     * @throws PortalException if the user's information was invalid or if the
     *         email address was reserved
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateIncompleteUser(
            long companyId, boolean autoPassword, String password1,
            String password2, boolean autoScreenName, String screenName,
            String emailAddress, long facebookId, String openId, Locale locale,
            String firstName, String middleName, String lastName, int prefixId,
            int suffixId, boolean male, int birthdayMonth, int birthdayDay,
            int birthdayYear, String jobTitle, boolean updateUserInformation,
            boolean sendEmail, ServiceContext serviceContext)
        throws PortalException, SystemException {

        long creatorUserId = 0;

        try {
            creatorUserId = getGuestOrUserId();
        }
        catch (PrincipalException pe) {
        }

        checkAddUserPermission(
            creatorUserId, companyId, emailAddress, null, null, null, null,
            serviceContext);

        return userLocalService.updateIncompleteUser(
            creatorUserId, companyId, autoPassword, password1, password2,
            autoScreenName, screenName, emailAddress, facebookId, openId,
            locale, firstName, middleName, lastName, prefixId, suffixId, male,
            birthdayMonth, birthdayDay, birthdayYear, jobTitle,
            updateUserInformation, sendEmail, serviceContext);
    }

    /**
     * Updates whether the user is locked out from logging in.
     *
     * @param  userId the primary key of the user
     * @param  lockout whether the user is locked out
     * @return the user
     * @throws PortalException if the user did not have permission to lock out
     *         the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateLockoutById(long userId, boolean lockout)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.DELETE);

        return userLocalService.updateLockoutById(userId, lockout);
    }

    /**
     * Updates the user's OpenID.
     *
     * @param  userId the primary key of the user
     * @param  openId the new OpenID
     * @return the user
     * @throws PortalException if a user with the primary key could not be found
     *         or if the current user did not have permission to update the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateOpenId(long userId, String openId)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.UPDATE);

        return userLocalService.updateOpenId(userId, openId);
    }

    /**
     * Sets the organizations that the user is in, removing and adding
     * organizations as necessary.
     *
     * @param  userId the primary key of the user
     * @param  organizationIds the primary keys of the organizations
     * @param  serviceContext the service context to be applied. Must set
     *         whether user indexing is enabled.
     * @throws PortalException if a user with the primary key could not be found
     *         or if the current user did not have permission to update the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void updateOrganizations(
            long userId, long[] organizationIds, ServiceContext serviceContext)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.UPDATE);

        checkOrganizations(userId, organizationIds);

        userLocalService.updateOrganizations(
            userId, organizationIds, serviceContext);
    }

    /**
     * Updates the user's password without tracking or validation of the change.
     *
     * @param  userId the primary key of the user
     * @param  password1 the user's new password
     * @param  password2 the user's new password confirmation
     * @param  passwordReset whether the user should be asked to reset their
     *         password the next time they log in
     * @return the user
     * @throws PortalException if a user with the primary key could not be found
     *         or if the current user did not have permission to update the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updatePassword(
            long userId, String password1, String password2,
            boolean passwordReset)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.UPDATE);

        return userLocalService.updatePassword(
            userId, password1, password2, passwordReset);
    }

    /**
     * Updates the user's portrait image.
     *
     * @param  userId the primary key of the user
     * @param  bytes the new portrait image data
     * @return the user
     * @throws PortalException if a user with the primary key could not be
     *         found, if the new portrait was invalid, or if the current user
     *         did not have permission to update the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updatePortrait(long userId, byte[] bytes)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.UPDATE);

        return userLocalService.updatePortrait(userId, bytes);
    }

    /**
     * Updates the user's password reset question and answer.
     *
     * @param  userId the primary key of the user
     * @param  question the user's new password reset question
     * @param  answer the user's new password reset answer
     * @return the user
     * @throws PortalException if a user with the primary key could not be
     *         found, if the new question or answer were invalid, or if the
     *         current user did not have permission to update the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateReminderQuery(long userId, String question, String answer)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.UPDATE);

        return userLocalService.updateReminderQuery(userId, question, answer);
    }

    /**
     * Updates the user's screen name.
     *
     * @param  userId the primary key of the user
     * @param  screenName the user's new screen name
     * @return the user
     * @throws PortalException if a user with the primary key could not be
     *         found, if the new screen name was invalid, or if the current user
     *         did not have permission to update the user
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateScreenName(long userId, String screenName)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.UPDATE);

        return userLocalService.updateScreenName(userId, screenName);
    }

    /**
     * Updates the user's workflow status.
     *
     * @param      userId the primary key of the user
     * @param      status the user's new workflow status
     * @return     the user
     * @throws     PortalException if a user with the primary key could not be
     *             found, if the current user was updating her own status to
     *             anything but {@link
     *             com.liferay.portal.kernel.workflow.WorkflowConstants#STATUS_APPROVED},
     *             or if the current user did not have permission to update the
     *             user's workflow status.
     * @throws     SystemException if a system exception occurred
     * @deprecated As of 7.0.0, replaced by {@link #updateStatus(long, int,
     *             ServiceContext)}
     */
    @Deprecated
    @Override
    public User updateStatus(long userId, int status)
        throws PortalException, SystemException {

        return updateStatus(userId, status, new ServiceContext());
    }

    /**
     * Updates the user's workflow status.
     *
     * @param  userId the primary key of the user
     * @param  status the user's new workflow status
     * @param  serviceContext the service context to be applied. You can specify
     *         an unencrypted custom password (used by an LDAP listener) for the
     *         user via attribute <code>passwordUnencrypted</code>.
     * @return the user
     * @throws PortalException if a user with the primary key could not be
     *         found, if the current user was updating her own status to
     *         anything but {@link
     *         com.liferay.portal.kernel.workflow.WorkflowConstants#STATUS_APPROVED},
     *         or if the current user did not have permission to update the
     *         user's workflow status.
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateStatus(
            long userId, int status, ServiceContext serviceContext)
        throws PortalException, SystemException {

        if ((getUserId() == userId) &&
            (status != WorkflowConstants.STATUS_APPROVED)) {

            throw new RequiredUserException();
        }

        UserPermissionUtil.check(
            getPermissionChecker(), userId, ActionKeys.DELETE);

        return userLocalService.updateStatus(userId, status, serviceContext);
    }

    /**
     * Updates the user with additional parameters.
     *
     * @param  userId the primary key of the user
     * @param  oldPassword the user's old password
     * @param  newPassword1 the user's new password (optionally
     *         <code>null</code>)
     * @param  newPassword2 the user's new password confirmation (optionally
     *         <code>null</code>)
     * @param  passwordReset whether the user should be asked to reset their
     *         password the next time they login
     * @param  reminderQueryQuestion the user's new password reset question
     * @param  reminderQueryAnswer the user's new password reset answer
     * @param  screenName the user's new screen name
     * @param  emailAddress the user's new email address
     * @param  facebookId the user's new Facebook ID
     * @param  openId the user's new OpenID
     * @param  languageId the user's new language ID
     * @param  timeZoneId the user's new time zone ID
     * @param  greeting the user's new greeting
     * @param  comments the user's new comments
     * @param  firstName the user's new first name
     * @param  middleName the user's new middle name
     * @param  lastName the user's new last name
     * @param  prefixId the user's new name prefix ID
     * @param  suffixId the user's new name suffix ID
     * @param  male whether user is male
     * @param  birthdayMonth the user's new birthday month (0-based, meaning 0
     *         for January)
     * @param  birthdayDay the user's new birthday day
     * @param  birthdayYear the user's birthday year
     * @param  smsSn the user's new SMS screen name
     * @param  aimSn the user's new AIM screen name
     * @param  facebookSn the user's new Facebook screen name
     * @param  icqSn the user's new ICQ screen name
     * @param  jabberSn the user's new Jabber screen name
     * @param  msnSn the user's new MSN screen name
     * @param  mySpaceSn the user's new MySpace screen name
     * @param  skypeSn the user's new Skype screen name
     * @param  twitterSn the user's new Twitter screen name
     * @param  ymSn the user's new Yahoo! Messenger screen name
     * @param  jobTitle the user's new job title
     * @param  groupIds the primary keys of the user's groups
     * @param  organizationIds the primary keys of the user's organizations
     * @param  roleIds the primary keys of the user's roles
     * @param  userGroupRoles the user user's group roles
     * @param  userGroupIds the primary keys of the user's user groups
     * @param  addresses the user's addresses
     * @param  emailAddresses the user's email addresses
     * @param  phones the user's phone numbers
     * @param  websites the user's websites
     * @param  announcementsDelivers the announcements deliveries
     * @param  serviceContext the service context to be applied (optionally
     *         <code>null</code>). Can set the UUID (with the <code>uuid</code>
     *         attribute), asset category IDs, asset tag names, and expando
     *         bridge attributes for the user.
     * @return the user
     * @throws PortalException if a user with the primary key could not be
     *         found, if the new information was invalid, if the current user
     *         did not have permission to update the user, or if the operation
     *         was not allowed by the membership policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateUser(
            long userId, String oldPassword, String newPassword1,
            String newPassword2, boolean passwordReset,
            String reminderQueryQuestion, String reminderQueryAnswer,
            String screenName, String emailAddress, long facebookId,
            String openId, String languageId, String timeZoneId,
            String greeting, String comments, String firstName,
            String middleName, String lastName, int prefixId, int suffixId,
            boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
            String smsSn, String aimSn, String facebookSn, String icqSn,
            String jabberSn, String msnSn, String mySpaceSn, String skypeSn,
            String twitterSn, String ymSn, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds,
            List<UserGroupRole> userGroupRoles, long[] userGroupIds,
            List<Address> addresses, List<EmailAddress> emailAddresses,
            List<Phone> phones, List<Website> websites,
            List<AnnouncementsDelivery> announcementsDelivers,
            ServiceContext serviceContext)
        throws PortalException, SystemException {

        UserPermissionUtil.check(
            getPermissionChecker(), userId, organizationIds, ActionKeys.UPDATE);

        User user = userPersistence.findByPrimaryKey(userId);

        if (addresses != null) {
            UsersAdminUtil.updateAddresses(
                Contact.class.getName(), user.getContactId(), addresses);
        }

        if (emailAddresses != null) {
            UsersAdminUtil.updateEmailAddresses(
                Contact.class.getName(), user.getContactId(), emailAddresses);
        }

        if (phones != null) {
            UsersAdminUtil.updatePhones(
                Contact.class.getName(), user.getContactId(), phones);
        }

        if (websites != null) {
            UsersAdminUtil.updateWebsites(
                Contact.class.getName(), user.getContactId(), websites);
        }

        if (announcementsDelivers != null) {
            updateAnnouncementsDeliveries(
                user.getUserId(), announcementsDelivers);
        }

        long curUserId = getUserId();

        if (curUserId == userId) {
            emailAddress = StringUtil.toLowerCase(emailAddress.trim());

            if (!StringUtil.equalsIgnoreCase(
                    emailAddress, user.getEmailAddress())) {

                validateEmailAddress(user, emailAddress);
            }
        }

        validateUpdatePermission(
            user, screenName, emailAddress, firstName, middleName, lastName,
            prefixId, suffixId, birthdayMonth, birthdayDay, birthdayYear, male,
            jobTitle);

        // Group membership policy

        long[] oldGroupIds = user.getGroupIds();

        List<Long> addGroupIds = new ArrayList<Long>();
        List<Long> removeGroupIds = ListUtil.toList(oldGroupIds);

        if (groupIds != null) {
            groupIds = checkGroups(userId, groupIds);

            for (long groupId : groupIds) {
                if (ArrayUtil.contains(oldGroupIds, groupId)) {
                    removeGroupIds.remove(groupId);
                }
                else {
                    addGroupIds.add(groupId);
                }
            }

            if (!addGroupIds.isEmpty() || !removeGroupIds.isEmpty()) {
                SiteMembershipPolicyUtil.checkMembership(
                    new long[] {userId}, ArrayUtil.toLongArray(addGroupIds),
                    ArrayUtil.toLongArray(removeGroupIds));
            }
        }

        // Organization membership policy

        long[] oldOrganizationIds = user.getOrganizationIds();

        List<Long> addOrganizationIds = new ArrayList<Long>();
        List<Long> removeOrganizationIds = ListUtil.toList(oldOrganizationIds);

        if (organizationIds != null) {
            organizationIds = checkOrganizations(userId, organizationIds);

            for (long organizationId : organizationIds) {
                if (ArrayUtil.contains(oldOrganizationIds, organizationId)) {
                    removeOrganizationIds.remove(organizationId);
                }
                else {
                    addOrganizationIds.add(organizationId);
                }
            }

            if (!addOrganizationIds.isEmpty() ||
                !removeOrganizationIds.isEmpty()) {

                OrganizationMembershipPolicyUtil.checkMembership(
                    new long[] {userId},
                    ArrayUtil.toLongArray(addOrganizationIds),
                    ArrayUtil.toLongArray(removeOrganizationIds));
            }
        }

        // Role membership policy

        long[] oldRoleIds = user.getRoleIds();

        List<Long> addRoleIds = new ArrayList<Long>();
        List<Long> removeRoleIds = ListUtil.toList(oldRoleIds);

        if (roleIds != null) {
            roleIds = checkRoles(userId, roleIds);

            for (long roleId : roleIds) {
                if (ArrayUtil.contains(oldRoleIds, roleId)) {
                    removeRoleIds.remove(roleId);
                }
                else {
                    addRoleIds.add(roleId);
                }
            }

            if (!addRoleIds.isEmpty() || !removeRoleIds.isEmpty()) {
                RoleMembershipPolicyUtil.checkRoles(
                    new long[] {userId}, ArrayUtil.toLongArray(addRoleIds),
                    ArrayUtil.toLongArray(removeRoleIds));
            }
        }

        List<UserGroupRole> oldOrganizationUserGroupRoles =
            new ArrayList<UserGroupRole>();
        List<UserGroupRole> oldSiteUserGroupRoles =
            new ArrayList<UserGroupRole>();

        List<UserGroupRole> oldUserGroupRoles =
            userGroupRolePersistence.findByUserId(userId);

        for (UserGroupRole oldUserGroupRole : oldUserGroupRoles) {
            Role role = oldUserGroupRole.getRole();

            if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
                oldOrganizationUserGroupRoles.add(oldUserGroupRole);
            }
            else if (role.getType() == RoleConstants.TYPE_SITE) {
                oldSiteUserGroupRoles.add(oldUserGroupRole);
            }
        }

        List<UserGroupRole> addOrganizationUserGroupRoles =
            new ArrayList<UserGroupRole>();
        List<UserGroupRole> removeOrganizationUserGroupRoles = ListUtil.copy(
            oldOrganizationUserGroupRoles);
        List<UserGroupRole> addSiteUserGroupRoles =
            new ArrayList<UserGroupRole>();
        List<UserGroupRole> removeSiteUserGroupRoles = ListUtil.copy(
            oldSiteUserGroupRoles);

        if (userGroupRoles != null) {
            userGroupRoles = checkUserGroupRoles(userId, userGroupRoles);

            for (UserGroupRole userGroupRole : userGroupRoles) {
                Role role = userGroupRole.getRole();

                if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
                    if (oldOrganizationUserGroupRoles.contains(userGroupRole)) {
                        removeOrganizationUserGroupRoles.remove(userGroupRole);
                    }
                    else {
                        addOrganizationUserGroupRoles.add(userGroupRole);
                    }
                }
                else if (role.getType() == RoleConstants.TYPE_SITE) {
                    if (oldSiteUserGroupRoles.contains(userGroupRole)) {
                        removeSiteUserGroupRoles.remove(userGroupRole);
                    }
                    else {
                        addSiteUserGroupRoles.add(userGroupRole);
                    }
                }
            }

            if (!addOrganizationUserGroupRoles.isEmpty() ||
                !removeOrganizationUserGroupRoles.isEmpty()) {

                OrganizationMembershipPolicyUtil.checkRoles(
                    addOrganizationUserGroupRoles,
                    removeOrganizationUserGroupRoles);
            }

            if (!addSiteUserGroupRoles.isEmpty() ||
                !removeSiteUserGroupRoles.isEmpty()) {

                SiteMembershipPolicyUtil.checkRoles(
                    addSiteUserGroupRoles, removeSiteUserGroupRoles);
            }
        }

        // User group membership policy

        long[] oldUserGroupIds = user.getUserGroupIds();

        List<Long> addUserGroupIds = new ArrayList<Long>();
        List<Long> removeUserGroupIds = ListUtil.toList(oldUserGroupIds);

        if (userGroupIds != null) {
            userGroupIds = checkUserGroupIds(userId, userGroupIds);

            for (long userGroupId : userGroupIds) {
                if (ArrayUtil.contains(oldUserGroupIds, userGroupId)) {
                    removeUserGroupIds.remove(userGroupId);
                }
                else {
                    addUserGroupIds.add(userGroupId);
                }
            }

            if (!addUserGroupIds.isEmpty() || !removeUserGroupIds.isEmpty()) {
                UserGroupMembershipPolicyUtil.checkMembership(
                    new long[] {userId}, ArrayUtil.toLongArray(addUserGroupIds),
                    ArrayUtil.toLongArray(removeUserGroupIds));
            }
        }

        user = userLocalService.updateUser(
            userId, oldPassword, newPassword1, newPassword2, passwordReset,
            reminderQueryQuestion, reminderQueryAnswer, screenName,
            emailAddress, facebookId, openId, languageId, timeZoneId, greeting,
            comments, firstName, middleName, lastName, prefixId, suffixId, male,
            birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
            icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
            jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
            userGroupIds, serviceContext);

        if (!addGroupIds.isEmpty() || !removeGroupIds.isEmpty()) {
            SiteMembershipPolicyUtil.propagateMembership(
                new long[] {user.getUserId()},
                ArrayUtil.toLongArray(addGroupIds),
                ArrayUtil.toLongArray(removeGroupIds));
        }

        if (!addOrganizationIds.isEmpty() || !removeOrganizationIds.isEmpty()) {
            OrganizationMembershipPolicyUtil.propagateMembership(
                new long[] {user.getUserId()},
                ArrayUtil.toLongArray(addOrganizationIds),
                ArrayUtil.toLongArray(removeOrganizationIds));
        }

        if (!addRoleIds.isEmpty() || !removeRoleIds.isEmpty()) {
            RoleMembershipPolicyUtil.propagateRoles(
                new long[] {user.getUserId()},
                ArrayUtil.toLongArray(addRoleIds),
                ArrayUtil.toLongArray(removeRoleIds));
        }

        if (!addSiteUserGroupRoles.isEmpty() ||
            !removeSiteUserGroupRoles.isEmpty()) {

            SiteMembershipPolicyUtil.propagateRoles(
                addSiteUserGroupRoles, removeSiteUserGroupRoles);
        }

        if (!addOrganizationUserGroupRoles.isEmpty() ||
            !removeOrganizationUserGroupRoles.isEmpty()) {

            OrganizationMembershipPolicyUtil.propagateRoles(
                addOrganizationUserGroupRoles,
                removeOrganizationUserGroupRoles);
        }

        if (!addUserGroupIds.isEmpty() || !removeGroupIds.isEmpty()) {
            UserGroupMembershipPolicyUtil.propagateMembership(
                new long[] {user.getUserId()},
                ArrayUtil.toLongArray(addUserGroupIds),
                ArrayUtil.toLongArray(removeUserGroupIds));
        }

        return user;
    }

    /**
     * Updates the user.
     *
     * @param  userId the primary key of the user
     * @param  oldPassword the user's old password
     * @param  newPassword1 the user's new password (optionally
     *         <code>null</code>)
     * @param  newPassword2 the user's new password confirmation (optionally
     *         <code>null</code>)
     * @param  passwordReset whether the user should be asked to reset their
     *         password the next time they login
     * @param  reminderQueryQuestion the user's new password reset question
     * @param  reminderQueryAnswer the user's new password reset answer
     * @param  screenName the user's new screen name
     * @param  emailAddress the user's new email address
     * @param  facebookId the user's new Facebook ID
     * @param  openId the user's new OpenID
     * @param  languageId the user's new language ID
     * @param  timeZoneId the user's new time zone ID
     * @param  greeting the user's new greeting
     * @param  comments the user's new comments
     * @param  firstName the user's new first name
     * @param  middleName the user's new middle name
     * @param  lastName the user's new last name
     * @param  prefixId the user's new name prefix ID
     * @param  suffixId the user's new name suffix ID
     * @param  male whether user is male
     * @param  birthdayMonth the user's new birthday month (0-based, meaning 0
     *         for January)
     * @param  birthdayDay the user's new birthday day
     * @param  birthdayYear the user's birthday year
     * @param  smsSn the user's new SMS screen name
     * @param  aimSn the user's new AIM screen name
     * @param  facebookSn the user's new Facebook screen name
     * @param  icqSn the user's new ICQ screen name
     * @param  jabberSn the user's new Jabber screen name
     * @param  msnSn the user's new MSN screen name
     * @param  mySpaceSn the user's new MySpace screen name
     * @param  skypeSn the user's new Skype screen name
     * @param  twitterSn the user's new Twitter screen name
     * @param  ymSn the user's new Yahoo! Messenger screen name
     * @param  jobTitle the user's new job title
     * @param  groupIds the primary keys of the user's groups
     * @param  organizationIds the primary keys of the user's organizations
     * @param  roleIds the primary keys of the user's roles
     * @param  userGroupRoles the user user's group roles
     * @param  userGroupIds the primary keys of the user's user groups
     * @param  serviceContext the service context to be applied (optionally
     *         <code>null</code>). Can set the UUID (with the <code>uuid</code>
     *         attribute), asset category IDs, asset tag names, and expando
     *         bridge attributes for the user.
     * @return the user
     * @throws PortalException if a user with the primary key could not be
     *         found, if the new information was invalid, if the current user
     *         did not have permission to update the user, or if the operation
     *         was not allowed by the membership policy
     * @throws SystemException if a system exception occurred
     */
    @Override
    public User updateUser(
            long userId, String oldPassword, String newPassword1,
            String newPassword2, boolean passwordReset,
            String reminderQueryQuestion, String reminderQueryAnswer,
            String screenName, String emailAddress, long facebookId,
            String openId, String languageId, String timeZoneId,
            String greeting, String comments, String firstName,
            String middleName, String lastName, int prefixId, int suffixId,
            boolean male, int birthdayMonth, int birthdayDay, int birthdayYear,
            String smsSn, String aimSn, String facebookSn, String icqSn,
            String jabberSn, String msnSn, String mySpaceSn, String skypeSn,
            String twitterSn, String ymSn, String jobTitle, long[] groupIds,
            long[] organizationIds, long[] roleIds,
            List<UserGroupRole> userGroupRoles, long[] userGroupIds,
            ServiceContext serviceContext)
        throws PortalException, SystemException {

        return updateUser(
            userId, oldPassword, newPassword1, newPassword2, passwordReset,
            reminderQueryQuestion, reminderQueryAnswer, screenName,
            emailAddress, facebookId, openId, languageId, timeZoneId, greeting,
            comments, firstName, middleName, lastName, prefixId, suffixId, male,
            birthdayMonth, birthdayDay, birthdayYear, smsSn, aimSn, facebookSn,
            icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn, ymSn,
            jobTitle, groupIds, organizationIds, roleIds, userGroupRoles,
            userGroupIds, null, null, null, null, null, serviceContext);
    }

    protected void checkAddUserPermission(
            long creatorUserId, long companyId, String emailAddress,
            long[] groupIds, long[] organizationIds, long[] roleIds,
            long[] userGroupIds, ServiceContext serviceContext)
        throws PortalException, SystemException {

        Company company = companyPersistence.findByPrimaryKey(companyId);

        if (groupIds != null) {
            checkGroups(0, groupIds);
        }

        if (organizationIds != null) {
            checkOrganizations(0, organizationIds);
        }

        if (roleIds != null) {
            checkRoles(0, roleIds);
        }

        if (userGroupIds != null) {
            checkUserGroupIds(0, userGroupIds);
        }

        boolean anonymousUser = ParamUtil.getBoolean(
            serviceContext, "anonymousUser");

        long defaultUserId = userLocalService.getDefaultUserId(companyId);

        if (((creatorUserId != 0) && (creatorUserId != defaultUserId)) ||
            (!company.isStrangers() && !anonymousUser)) {

            if (!PortalPermissionUtil.contains(
                    getPermissionChecker(), ActionKeys.ADD_USER) &&
                !OrganizationPermissionUtil.contains(
                    getPermissionChecker(), organizationIds,
                    ActionKeys.ASSIGN_MEMBERS)) {

                throw new PrincipalException();
            }
        }

        if ((creatorUserId == 0) || (creatorUserId == defaultUserId)) {
            if (!company.isStrangersWithMx() &&
                company.hasCompanyMx(emailAddress)) {

                throw new ReservedUserEmailAddressException();
            }
        }
    }

    protected long[] checkGroups(long userId, long[] groupIds)
        throws PortalException, SystemException {

        long[] oldGroupIds = null;

        PermissionChecker permissionChecker = getPermissionChecker();

        User user = null;

        if (userId != CompanyConstants.SYSTEM) {

            // Add back any mandatory groups or groups that the administrator
            // does not have the rights to remove and check that he has the
            // permission to add a new group

            user = userPersistence.findByPrimaryKey(userId);

            List<Group> oldGroups = groupLocalService.getUserGroups(userId);

            oldGroupIds = new long[oldGroups.size()];

            for (int i = 0; i < oldGroups.size(); i++) {
                Group group = oldGroups.get(i);

                if (!ArrayUtil.contains(groupIds, group.getGroupId()) &&
                    (!GroupPermissionUtil.contains(
                        permissionChecker, group.getGroupId(),
                        ActionKeys.ASSIGN_MEMBERS) ||
                     SiteMembershipPolicyUtil.isMembershipProtected(
                         permissionChecker, user.getUserId(),
                         group.getGroupId()) ||
                     SiteMembershipPolicyUtil.isMembershipRequired(
                         userId, group.getGroupId()))) {

                    groupIds = ArrayUtil.append(groupIds, group.getGroupId());
                }

                oldGroupIds[i] = group.getGroupId();
            }
        }

        // Check that the administrator has the permission to add a new group
        // and that the group membership is allowed

        for (long groupId : groupIds) {
            if ((oldGroupIds != null) &&
                ArrayUtil.contains(oldGroupIds, groupId)) {

                continue;
            }

            Group group = groupPersistence.findByPrimaryKey(groupId);

            GroupPermissionUtil.check(
                permissionChecker, group, ActionKeys.ASSIGN_MEMBERS);
        }

        return groupIds;
    }

    protected void checkMembership(
            long[] userIds, long[] groupIds, long[] organizationIds,
            long[] roleIds, long[] userGroupIds)
        throws PortalException, SystemException {

        if (groupIds != null) {
            SiteMembershipPolicyUtil.checkMembership(userIds, groupIds, null);
        }

        if (organizationIds != null) {
            OrganizationMembershipPolicyUtil.checkMembership(
                userIds, organizationIds, null);
        }

        if (roleIds != null) {
            RoleMembershipPolicyUtil.checkRoles(userIds, roleIds, null);
        }

        if (userGroupIds != null) {
            UserGroupMembershipPolicyUtil.checkMembership(
                userIds, userGroupIds, null);
        }
    }

    protected long[] checkOrganizations(long userId, long[] organizationIds)
        throws PortalException, SystemException {

        long[] oldOrganizationIds = null;

        PermissionChecker permissionChecker = getPermissionChecker();

        if (userId != CompanyConstants.SYSTEM) {

            // Add back any mandatory organizations or organizations that the
            // administrator does not have the rights to remove and check that
            // he has the permission to add a new organization

            List<Organization> oldOrganizations =
                organizationLocalService.getUserOrganizations(userId);

            oldOrganizationIds = new long[oldOrganizations.size()];

            for (int i = 0; i < oldOrganizations.size(); i++) {
                Organization organization = oldOrganizations.get(i);

                if (!ArrayUtil.contains(
                        organizationIds, organization.getOrganizationId()) &&
                    (!OrganizationPermissionUtil.contains(
                        permissionChecker, organization.getOrganizationId(),
                        ActionKeys.ASSIGN_MEMBERS) ||
                     OrganizationMembershipPolicyUtil.isMembershipProtected(
                        permissionChecker, userId,
                        organization.getOrganizationId()) ||
                     OrganizationMembershipPolicyUtil.isMembershipRequired(
                        userId, organization.getOrganizationId()))) {

                    organizationIds = ArrayUtil.append(
                        organizationIds, organization.getOrganizationId());
                }

                oldOrganizationIds[i] = organization.getOrganizationId();
            }
        }

        // Check that the administrator has the permission to add a new
        // organization and that the organization membership is allowed

        for (long organizationId : organizationIds) {
            if ((oldOrganizationIds != null) &&
                ArrayUtil.contains(oldOrganizationIds, organizationId)) {

                continue;
            }

            Organization organization =
                organizationPersistence.findByPrimaryKey(organizationId);

            OrganizationPermissionUtil.check(
                permissionChecker, organization, ActionKeys.ASSIGN_MEMBERS);
        }

        return organizationIds;
    }

    protected long[] checkRoles(long userId, long[] roleIds)
        throws PortalException, SystemException {

        long[] oldRoleIds = null;

        PermissionChecker permissionChecker = getPermissionChecker();

        if (userId != CompanyConstants.SYSTEM) {

            // Add back any mandatory roles or roles that the administrator does
            // not have the rights to remove and check that he has the
            // permission to add a new role

            List<Role> oldRoles = roleLocalService.getUserRoles(userId);

            oldRoleIds = new long[oldRoles.size()];

            for (int i = 0; i < oldRoles.size(); i++) {
                Role role = oldRoles.get(i);

                if (!ArrayUtil.contains(roleIds, role.getRoleId()) &&
                    (!RolePermissionUtil.contains(
                        permissionChecker, role.getRoleId(),
                        ActionKeys.ASSIGN_MEMBERS) ||
                     RoleMembershipPolicyUtil.isRoleRequired(
                        userId, role.getRoleId()))) {

                    roleIds = ArrayUtil.append(roleIds, role.getRoleId());
                }

                oldRoleIds[i] = role.getRoleId();
            }
        }

        // Check that the administrator has the permission to add a new role and
        // that the role membership is allowed

        for (long roleId : roleIds) {
            if ((oldRoleIds != null) &&
                ArrayUtil.contains(oldRoleIds, roleId)) {

                continue;
            }

            RolePermissionUtil.check(
                permissionChecker, roleId, ActionKeys.ASSIGN_MEMBERS);
        }

        if (userId != CompanyConstants.SYSTEM) {
            return UsersAdminUtil.addRequiredRoles(userId, roleIds);
        }

        return roleIds;
    }

    protected long[] checkUserGroupIds(long userId, long[] userGroupIds)
        throws PortalException, SystemException {

        long[] oldUserGroupIds = null;

        PermissionChecker permissionChecker = getPermissionChecker();

        if (userId != CompanyConstants.SYSTEM) {

            // Add back any user groups that the administrator does not have the
            // rights to remove or that have a mandatory membership

            List<UserGroup> oldUserGroups =
                userGroupLocalService.getUserUserGroups(userId);

            oldUserGroupIds = new long[oldUserGroups.size()];

            for (int i = 0; i < oldUserGroups.size(); i++) {
                UserGroup userGroup = oldUserGroups.get(i);

                if (!ArrayUtil.contains(
                        userGroupIds, userGroup.getUserGroupId()) &&
                    (!UserGroupPermissionUtil.contains(
                        permissionChecker, userGroup.getUserGroupId(),
                        ActionKeys.ASSIGN_MEMBERS) ||
                     UserGroupMembershipPolicyUtil.isMembershipRequired(
                        userId, userGroup.getUserGroupId()))) {

                    userGroupIds = ArrayUtil.append(
                        userGroupIds, userGroup.getUserGroupId());
                }

                oldUserGroupIds[i] = userGroup.getUserGroupId();
            }
        }

        // Check that the administrator has the permission to add a new user
        // group and that the user group membership is allowed

        for (long userGroupId : userGroupIds) {
            if ((oldUserGroupIds == null) ||
                !ArrayUtil.contains(oldUserGroupIds, userGroupId)) {

                UserGroupPermissionUtil.check(
                    permissionChecker, userGroupId, ActionKeys.ASSIGN_MEMBERS);
            }
        }

        return userGroupIds;
    }

    protected List<UserGroupRole> checkUserGroupRoles(
            long userId, List<UserGroupRole> userGroupRoles)
        throws PortalException, SystemException {

        List<UserGroupRole> oldUserGroupRoles = null;

        PermissionChecker permissionChecker = getPermissionChecker();

        if (userId != CompanyConstants.SYSTEM) {

            // Add back any user group roles that the administrator does not
            // have the rights to remove or that have a mandatory membership

            oldUserGroupRoles = userGroupRoleLocalService.getUserGroupRoles(
                userId);

            for (UserGroupRole oldUserGroupRole : oldUserGroupRoles) {
                Role role = oldUserGroupRole.getRole();
                Group group = oldUserGroupRole.getGroup();

                if (userGroupRoles.contains(oldUserGroupRole)) {
                    continue;
                }

                if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
                    Organization organization =
                        organizationPersistence.findByPrimaryKey(
                            group.getOrganizationId());

                    if (!UserGroupRolePermissionUtil.contains(
                            permissionChecker, oldUserGroupRole.getGroupId(),
                            oldUserGroupRole.getRoleId()) ||
                        OrganizationMembershipPolicyUtil.isRoleProtected(
                            getPermissionChecker(), userId,
                            organization.getOrganizationId(),
                            role.getRoleId()) ||
                        OrganizationMembershipPolicyUtil.isRoleRequired(
                            userId, organization.getOrganizationId(),
                            role.getRoleId())) {

                        userGroupRoles.add(oldUserGroupRole);
                    }
                }
                else if (role.getType() == RoleConstants.TYPE_SITE) {
                    if (!userGroupRoles.contains(oldUserGroupRole) &&
                        (!UserGroupRolePermissionUtil.contains(
                            permissionChecker, oldUserGroupRole.getGroupId(),
                            oldUserGroupRole.getRoleId()) ||
                         SiteMembershipPolicyUtil.isRoleProtected(
                             getPermissionChecker(), userId, group.getGroupId(),
                             role.getRoleId()) ||
                         SiteMembershipPolicyUtil.isRoleRequired(
                             userId, group.getGroupId(), role.getRoleId()))) {

                        userGroupRoles.add(oldUserGroupRole);
                    }
                }
            }
        }

        // Check that the administrator has the permission to add a new user
        // group role and that the user group role membership is allowed

        for (UserGroupRole userGroupRole : userGroupRoles) {
            if ((oldUserGroupRoles == null) ||
                !oldUserGroupRoles.contains(userGroupRole)) {

                UserGroupRolePermissionUtil.check(
                    permissionChecker, userGroupRole.getGroupId(),
                    userGroupRole.getRoleId());
            }
        }

        return userGroupRoles;
    }

    protected void propagateMembership(
            long[] userIds, long[] groupIds, long[] organizationIds,
            long[] roleIds, long[] userGroupIds)
        throws PortalException, SystemException {

        if (groupIds != null) {
            SiteMembershipPolicyUtil.propagateMembership(
                userIds, groupIds, null);
        }

        if (organizationIds != null) {
            OrganizationMembershipPolicyUtil.propagateMembership(
                userIds, organizationIds, null);
        }

        if (roleIds != null) {
            RoleMembershipPolicyUtil.propagateRoles(userIds, roleIds, null);
        }

        if (userGroupIds != null) {
            UserGroupMembershipPolicyUtil.propagateMembership(
                userIds, userGroupIds, null);
        }
    }

    protected void updateAnnouncementsDeliveries(
            long userId, List<AnnouncementsDelivery> announcementsDeliveries)
        throws PortalException, SystemException {

        for (AnnouncementsDelivery announcementsDelivery :
                announcementsDeliveries) {

            announcementsDeliveryService.updateDelivery(
                userId, announcementsDelivery.getType(),
                announcementsDelivery.getEmail(),
                announcementsDelivery.getSms(),
                announcementsDelivery.getWebsite());
        }
    }

    protected void validateEmailAddress(User user, String emailAddress)
        throws PortalException, SystemException {

        if (!user.hasCompanyMx() && user.hasCompanyMx(emailAddress)) {
            Company company = companyPersistence.findByPrimaryKey(
                user.getCompanyId());

            if (!company.isStrangersWithMx()) {
                throw new ReservedUserEmailAddressException();
            }
        }
    }

    protected void validateOrganizationUsers(long[] userIds)
        throws PortalException, SystemException {

        PermissionChecker permissionChecker = getPermissionChecker();

        if (!PropsValues.ORGANIZATIONS_ASSIGNMENT_STRICT ||
            permissionChecker.isCompanyAdmin()) {

            return;
        }

        for (long userId : userIds) {
            boolean allowed = false;

            List<Organization> organizations =
                organizationLocalService.getUserOrganizations(userId);

            for (Organization organization : organizations) {
                if (OrganizationPermissionUtil.contains(
                        permissionChecker, organization,
                        ActionKeys.MANAGE_USERS)) {

                    allowed = true;

                    break;
                }
            }

            if (!allowed) {
                throw new PrincipalException();
            }
        }
    }

    protected void validateUpdatePermission(
            User user, String screenName, String emailAddress, String firstName,
            String middleName, String lastName, int prefixId, int suffixId,
            int birthdayMonth, int birthdayDay, int birthdayYear, boolean male,
            String jobTitle)
        throws PortalException, SystemException {

        List<String> fields = new ArrayList<String>();

        Contact contact = user.getContact();

        Calendar birthday = CalendarFactoryUtil.getCalendar();

        birthday.setTime(contact.getBirthday());

        if ((birthdayMonth != birthday.get(Calendar.MONTH)) ||
            (birthdayDay != birthday.get(Calendar.DAY_OF_MONTH)) ||
            (birthdayYear != birthday.get(Calendar.YEAR))) {

            fields.add("birthday");
        }

        if (!StringUtil.equalsIgnoreCase(
                emailAddress, user.getEmailAddress())) {

            fields.add("emailAddress");
        }

        if (!StringUtil.equalsIgnoreCase(firstName, user.getFirstName())) {
            fields.add("firstName");
        }

        if (male != contact.getMale()) {
            fields.add("gender");
        }

        if (!StringUtil.equalsIgnoreCase(jobTitle, user.getJobTitle())) {
            fields.add("jobTitle");
        }

        if (!StringUtil.equalsIgnoreCase(lastName, user.getLastName())) {
            fields.add("lastName");
        }

        if (!StringUtil.equalsIgnoreCase(middleName, user.getMiddleName())) {
            fields.add("middleName");
        }

        if (prefixId != contact.getPrefixId()) {
            fields.add("prefix");
        }

        if (!StringUtil.equalsIgnoreCase(screenName, user.getScreenName())) {
            fields.add("screenName");
        }

        if (suffixId != contact.getSuffixId()) {
            fields.add("suffix");
        }

        UserFieldException ufe = new UserFieldException();

        for (String field : fields) {
            if (!UsersAdminUtil.hasUpdateFieldPermission(user, field)) {
                ufe.addField(field);
            }
        }

        if (ufe.hasFields()) {
            throw ufe;
        }
    }

}
