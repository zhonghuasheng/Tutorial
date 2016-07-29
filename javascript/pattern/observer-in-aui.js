AUI().use('event-custom', 'node', function(A) {
    var observer = {
        addSubscriber: function(callback) {
            this.subscribers[this.subscribers.length] = callback;
        },

        publish: function(val) {
            for(var i = 0; i < this.subscribers.length; i++) {
                if (typeof this.subscribers[i] === 'function') {
                    this.subscribers[i](val);
                }
            }
        },

        switcher: function(obj) {
            obj.subscribers = [];

            for(var i in this) {
                obj[i] = this[i];
            }
        },

        test: function(val) {
            console.log(val);
        }
    };

    A.observer = observer;
});

        var select = AUI().one('[name*=Select3286]');

        AUI().observer.switcher(select);

        var tester1 = {
            show: function(val) {
                console.log('tester1: ' + val);
            }
        };

    var tester2 = {
        show: function(val) {
        console.log('tester2: ' + val);
            }
    };

AUI().observer.switcher(tester1);
select.addSubscriber(tester1.show);
select.addSubscriber(tester2.show);
tester1.addSubscriber(tester2.show);

select.on('change', function(val) {
    var selected = this.one('option:selected').val();
    select.publish('select publish ' + selected);
    tester1.publish('tester1 publish ' + selected);
});