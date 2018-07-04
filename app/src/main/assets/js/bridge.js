/***************************************************************************
 * WKWebView native object
 **************************************************************************/
var WKWebViewNativeOBJ = window;
var SBNWebViewBridge = AndroidJSKit;
var mraid = window.mraid = {};

var log = {};
log.i = function(msg) {
    console.log("cjf---js_mraid "+msg);
//    WKWebViewNativeOBJ.SBNWebViewBridge.postMessage(msg); //for test
}

var VERSION = "1.0";
log.i("mraid init version=" + VERSION);

var EVENTS = mraid.EVENTS = {
    "ERROR" : "error",
    "READY" : "ready",
    "DEVICEINFO" : 'deviceinfo',
    "USERINFO"   : 'userinfo',
    "LOCATIONCHANGE": 'locationChange',
    "ORIENTATIONCHANGE": 'orientationChange',
    "NETWORKCHANGE": 'networkChange',
    "GETSIGN":'getSign',
    "COPYTOPASTEBOARD":'copyToPasteboard',
    "GETINVITATIONCODE":'getInvitationCode',
};


//function onGetDevideInfoListener1() {
//            console.log("onGetDevideInfoListener begin");
//
//            var args = Array.prototype.slice.call(arguments);
//            alert("onGetDevideInfoListener " + " [" + args.toString() + "]");
//
//            window.mraid.removeEventListener(window.mraid.EVENTS.DEVICEINFO, onGetDevideInfoListener1);
//        }
//
//function onGetDevideInfo1() {
//            console.log("onGetDevideInfo begin");
//            window.mraid.addEventListener(window.mraid.EVENTS.DEVICEINFO, onGetDevideInfoListener1);
//}


//function getSignCallback(sign) {
//     log.i("getSignCallback: " + sign);
//     jsonObj = JSON.parse(sign);
//     log.i("jsonObj" + jsonObj);
//     log.i("1 userId:" + jsonObj.userId + "\n" + "token:" + jsonObj.token);
//     log.i("2 userId:" + jsonObj["userId"] + "\n" + "token:" + jsonObj["token"]);
//}
//
//var params = {
//     "userId": "58233bc31d899567843be529",
//     "token": "ppag7434f69f1746",
//     "did": "ojb0awjLu7hXe8W3OgJiahSYavRA",
//     "appName": 5,
//     "os": 2,
//     "sourceType": 1,
//     "timestamp": 1496990695000,
//     "thirdPartyType": 1,
//     "openId": "9A4577B930B1C98047766FB52160BF36",
//     "appCode": "hfQuZaY"
//};
//
//mraid.getSignTest = function(params, getSignCallback) {
//     var signString = JSON.stringify(params);
//     log.i("getSignTest: " + signString);
//
//     getSignCallback(signString);
//
//};

// 初始化WebView
function webViewInit() {
      console.log("webViewInit begin");

//    onGetDevideInfo1(); //for test
//    changeEventNative('deviceinfo', 1); //for test

//     window.mraid.getSignTest(params, getSignCallback);//for test

}

window.onload = webViewInit;

/***************************************************************************
 * "official" API: methods called by creative
 **************************************************************************/
var listeners = {};

mraid.addEventListener = function(event, listener) {
    log.i("mraid.addEventListener " + event + ": " + String(listener));

    if (!event || !listener) {
        mraid.fireErrorEvent("Both event and listener are required.", "addEventListener");
        return;
    }
    if (!contains(event, EVENTS)) {
        mraid.fireErrorEvent("Unknown MRAID event: " + event, "addEventListener");
        return;
    }
    var listenersForEvent = listeners[event] = listeners[event] || [];
    // check to make sure that the listener isn't already registered
    for (var i = 0; i < listenersForEvent.length; i++) {
        var str1 = String(listener);
        var str2 = String(listenersForEvent[i]);
        if (listener === listenersForEvent[i] || str1 === str2) {
            log.i("listener " + str1 + " is already registered for event " + event);
            return;
        }
    }
    changeEventNative(event, 1);
    listenersForEvent.push(listener);
};

mraid.removeEventListener = function(event, listener) {
    log.i("mraid.removeEventListener " + event + " : " + String(listener));

    if (!event) {
        mraid.fireErrorEvent("Event is required.", "removeEventListener");
        return;
    }
    if (!contains(event, EVENTS)) {
        mraid.fireErrorEvent("Unknown MRAID event: " + event, "removeEventListener");
        return;
    }
    if (listeners.hasOwnProperty(event)) {
        if (listener) {
            var listenersForEvent = listeners[event];
            // try to find the given listener
            var len = listenersForEvent.length;
            for (var i = 0; i < len; i++) {
                var registeredListener = listenersForEvent[i];
                var str1 = String(listener);
                var str2 = String(registeredListener);
                if (listener === registeredListener || str1 === str2) {
                    listenersForEvent.splice(i, 1);
                    break;
                }
            }
            if (i === len) {
                log.i("listener " + str1 + " not found for event " + event);
            }
            if (listenersForEvent.length === 0) {
                changeEventNative(event, 0);
                delete listeners[event];
            }
        } else {
            // no listener to remove was provided, so remove all listeners for given event
            delete listeners[event];
        }
    } else {
        log.i("no listeners registered for event " + event);
    }
};

mraid.version = function(){
    return VERSION;
}

mraid.platform = function(){
    return "android";
}

mraid.getVersion = function() {
    log.i("mraid.getVersion");
    return VERSION;
};

mraid.getSign = function(params,callback){
    mraid.addEventListener(mraid.EVENTS.GETSIGN,callback);
    callNative("getSign?state=" + params);
}

mraid.copyToPasteboard = function(params,callback){
    mraid.addEventListener(mraid.EVENTS.GETSIGN,callback);
    callNative("COPYTOPASTEBOARD?state=" + params);
}

mraid.getInvitationCode = function(callback){
    mraid.addEventListener(mraid.EVENTS.GETSIGN,callback);
    callNative("GETINVITATIONCODE?state=1");
}

/***************************************************************************
 * methods to fire events
 **************************************************************************/
mraid.fireErrorEvent = function(message, action) {
    log.i("mraid.fireErrorEvent " + message + " " + action);
    fireEvent(mraid.EVENTS.ERROR, message, action);
};

mraid.fireReadyEvent = function() {
    log.i("mraid.fireReadyEvent");
    fireEvent(mraid.EVENTS.READY);
};

mraid.fireDeviceEvent = function(string){
    log.i("mraid.fireDeviceEvent " + string);

    fireEvent(mraid.EVENTS.DEVICEINFO, JSON.parse(string));
}

mraid.fireUserEvent = function(string){
    log.i("mraid.fireUserEvent " + string);
    fireEvent(mraid.EVENTS.USERINFO, JSON.parse(string));
}

mraid.fireGetSign = function(string){
    log.i("mraid.fireSignEvent " + string);
    fireEvent(mraid.EVENTS.GETSIGN, string);
    mraid.removeEventListener(mraid.EVENTS.GETSIGN, '');
}

mraid.fireCopyToPasteboard = function(string){
    log.i("mraid.fireCopyToPasteboard " + string);
    fireEvent(mraid.EVENTS.COPYTOPASTEBOARD, '');
    mraid.removeEventListener(mraid.EVENTS.COPYTOPASTEBOARD, '');
}

mraid.fireGetInvitationCode = function(string){
    log.i("mraid.fireGetInvitationCode " + string);
    fireEvent(mraid.EVENTS.GETINVITATIONCODE, string);
    mraid.removeEventListener(mraid.EVENTS.GETINVITATIONCODE, '');
}

/***************************************************************************
 * internal helper methods
 **************************************************************************/
function changeEventNative(event, state) {
    switch (event) {
        case EVENTS.DEVICEINFO:
            callNative("deviceinfo?state=" + state);
            break;
        case EVENTS.USERINFO:
            callNative("userinfo?state=" + state);
            break;
        case EVENTS.LOCATIONCHANGE:
            callNative("locationChange?state=" + state);
            break;
        case EVENTS.NETWORKCHANGE:
            callNative("networkChange?state=" + state);
            break;
        case EVENTS.ORIENTATIONCHANGE:
            callNative("orientationChange?state=" + state);
            break;
    }
}

var callNative = function(command) {
    WKWebViewNativeOBJ.SBNWebViewBridge.postMessage("mraid://"+command);
};

var fireEvent = function(event) {
    var args = Array.prototype.slice.call(arguments);
    args.shift();
    log.i("fireEvent " + event + " [" + args.toString() + "]");
    var eventListeners = listeners[event];
    if (eventListeners) {
        var len = eventListeners.length;
        log.i(len + " listener(s) found");
        for (var i = 0; i < len; i++) {
            eventListeners[i].apply(null, args);
        }
    } else {
        log.i("no listeners found");
    }
};

var contains = function(value, array) {
    for (var i in array) {
        if (array[i] === value) {
            return true;
        }
    }
    return false;
};

