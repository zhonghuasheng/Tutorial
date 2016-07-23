YUI.add('my-module', function (A) {
    A.MyModule = {
        sayHello: function () {
           console.log('ttt');
        }
    };
});

YUI.add('external', function(A) {
    A.External = {
        run: function() {
            console.log('...');
        }
    }
}, '1.0.0', { requires: [ 'node' ] });
