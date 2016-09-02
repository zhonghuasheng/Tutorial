### Validator

* isEmailAddress(String emailAddress)
```java

    private static Pattern _emailAddressPattern = Pattern.compile(
        "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@" +
        "(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");

    public static boolean isEmailAddress(String emailAddress) {
        Matcher matcher = _emailAddressPattern.matcher(emailAddress);

        return matcher.matches();
    }
```

* isIPv4Address(String ipAddress)
```java
    private static Pattern _ipv4AddressPattern = Pattern.compile(
        "^" +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
        "$");

    public static boolean isIPv4Address(String ipAddress) {
        Matcher matcher = _ipv4AddressPattern.matcher(ipAddress);

        return matcher.matches();
    }
```

```java
	public static final char OPEN_BRACKET = '[';
    public static final char CLOSE_BRACKET = ']';
    private static Pattern _ipv6AddressPattern = Pattern.compile(
    "^" +
    "\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|" +
    "(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|" +
    "((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
    "(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|" +
    "(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:" +
    "((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
    "(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|" +
    "(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|" +
    "((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
    "(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|" +
    "(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|" +
    "((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
    "(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|" +
    "(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|" +
    "((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
    "(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|" +
    "(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|" +
    "((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)" +
    "(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|" +
    "(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:" +
    "((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\." +
    "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*" +
    "$");

	public static boolean isIPv6Address(String ipAddress) {
		if (isNull(ipAddress)) {
			return false;
		}

		if (StringUtil.startsWith(ipAddress, CharPool.OPEN_BRACKET) &&
			StringUtil.endsWith(ipAddress, CharPool.CLOSE_BRACKET)) {

			ipAddress = ipAddress.substring(1, ipAddress.length() - 1);
		}

		Matcher matcher = _ipv6AddressPattern.matcher(ipAddress);

		return matcher.matches();
	}
```