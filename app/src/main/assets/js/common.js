window.onload = function() {
    console.log("cjf---window load");

    setTimeout("runDelay()","10");
//     setTimeout("onResize()","10");
    // setTimeout("fShowImage()","10");
}

//需要webview完全渲染完后，才能正确获取的信息，统一放在一个延时运行方法中
function runDelay() {
    console.log("cjf---runDelay");
    onResize();
    // fShowImage();

    // 兼容部分手机onload后，一段时间后坐标信息才稳定的问题。
    var _oldHeight = document.body.clientHeight;
    var i = 0;
    var timer = setInterval(function () {
        i++;
        console.log("cjf---runDelay" + i);

        if (i > 5) {
            clearInterval(timer);
        }
        var _newHeight = document.body.clientHeight;
        if (_newHeight != _oldHeight) {
            _oldHeight = _newHeight;
            AndroidJSKit.resize(_newHeight);
            // onResize();
            fShowImage();
        }
    },200);
}

//通知app内容大小变化
function onResize() {
    console.log("cjf---onResize");
    AndroidJSKit.resize(document.body.getBoundingClientRect().height);
}

//通知app图片点击事件
function onClickImage(position) {
    var obj = document.getElementsByTagName("img")[position];
	var offsetY=obj.offsetTop;
    window.AndroidJSKit.clickImage(offsetY, obj.id, position);
    if(position == 1){
        console.log("cjf--- "+document.body.innerHTML);
    }
}

//通知app占位图点击事件
function onClickPlaceholder(position) {
    var obj = document.getElementsByTagName("img")[position];
    window.AndroidJSKit.onClickPlaceholder(position);
}

//html添加占位符
function addImgPlaceholder() {
    var images = document.getElementsByTagName("img");
    for (var i = 0, len = images.length; i < len; i++) {
        var imgID = images[i].getAttribute("id");
        var imgOffsetLeft = images[i].offsetLeft;
        var imgOffsetTop = images[i].offsetTop;
        var imgWidth = images[i].offsetWidth;
        var imgHeight = images[i].offsetHeight;
//        console.log("cjf---addImgPlaceholder -- " + imgID);
        if(undefined == imgID) {
            break;
        }

        var newPlaceholder = document.createElement("div");
        newPlaceholder.setAttribute("id","loading_"+imgID);
        newPlaceholder.setAttribute("class","imgPlaceholder");
        newPlaceholder.setAttribute("onClick","onClickPlaceholder(" + i + ")");

        var fontSize = parseInt(window.getComputedStyle(document.documentElement).fontSize);
//        console.log("cjf---addImgPlaceholder -- fontSize = " + fontSize + ",  imgHeight = " + imgHeight);

        if(imgHeight >= fontSize + 15){
            newPlaceholder.innerHTML = "<div class='logo' onClick='onClickPlaceholder(" + i + ")'>搜狐新闻</div>";
        }

        var style = {
            width:imgWidth+"px",
            height: imgHeight+"px",
            top:imgOffsetTop+"px",
            left:imgOffsetLeft+"px"
        };
        for( var s in style) {
            newPlaceholder.style[s]=style[s];
        }

        if(images[i].getAttribute("src") === "") {
            document.body.appendChild(newPlaceholder);
        }
    }
}

//html移除占位符
function removePlaceholder(id){
    var ele = document.getElementById("loading_"+id);
    if(undefined == ele) {
        console.log("cjf---removePlaceholder id undefine id = " + id);
        return;
    }

    console.log("cjf---removePlaceholder -- " + id);

    if(ele.style.opacity === ""){
        ele.style.opacity = 1;
    }
    if(ele.style.display === "" || ele.style.display === 'none'){
        ele.style.display = 'block';
    }

    var t = setInterval(function(){
        if(ele.style.opacity >= 0){
            ele.style.opacity = Math.floor(ele.style.opacity*100)/100-0.05;
        }
        else{
            clearInterval(t);
            ele.style.display = 'none';
        }
    },1);
}

//app更新html下载好的新图片
function replaceImage(srcID, srcMD5) {
     console.log("cjf---replaceImage srcID -- " + srcID);

     var pics = document.getElementsByTagName("img");
     for (var i = pics.length - 1; i >= 0; i--) {
         // if srcID equal to id,  then replace src with srcMD5
         var id = pics[i].getAttribute("id");
         if(id == srcID + "_" + i) {
              pics[i].setAttribute("src", srcMD5);
              removePlaceholder(id);
         }
     }
}

// webview scroll结束后， 获取html可见区的img position list
function fGetImgIndex() { //for webview.
                console.log("cjf---fGetImgIndex start ");

            var _imgs = document.getElementsByTagName('img');
            var _timer = null;
            var _newsId;
            try {
                _newsId = document.getElementById('newsId').value;
            } catch(e) {
                _newsId = null;
            }

            if(undefined == _newsId) {
                console.log("cjf---fGetImgIndex _newsId undefine ");
                return;
            }

            var _WVHeight = window.innerHeight;
            var _fRenderImg = function() {
                if (_timer) {
                    clearTimeout(_timer);
                }
                _timer = setTimeout(function() {
                    var _imgUrlArr = [];

                    for (var i = 0, len = _imgs.length; i < len; i++) {
                        var _value = _imgs[i];
                        var _elToTop = _value.offsetTop;
                        var _scrollH = document.body.scrollTop;
                        if (_elToTop - _scrollH - _WVHeight <= 0 && _scrollH < _elToTop + _value.height) {
                            if (_value.src == '' || _value.src == 'about:blank') {
                                _imgUrlArr.push(i);
                            }
                        }
                    }

                    var _imgUrlArrFirst = _imgUrlArr[0];
                    var _imgsPrevious = _imgs[_imgUrlArrFirst-1];
                    var _imgUrlArrLast = _imgUrlArr[_imgUrlArr.length-1];
                    var _imgsNext = _imgs[_imgUrlArrLast + 1];

                    if (_imgUrlArrFirst>0 && (_imgsPrevious.src == '' || _imgsPrevious.src == 'about:blank')) {
                        _imgUrlArr.unshift(_imgUrlArrFirst-1);
                    }

                    if (_imgUrlArrLast < (_imgs.length - 1) && (_imgsNext.src == '' || _imgsNext.src == 'about:blank')) {
                        _imgUrlArr.push(_imgUrlArrLast+1);
                    }

                    if (_newsId) {
                        window.AndroidJSKit.scrollEndforImage(_newsId,_imgUrlArr);
                    }
                },500);
            };
            _fRenderImg();
            window.addEventListener('scroll',_fRenderImg);
     }

// 获取当前 html可见区的img position list
function fGetImgIndexByAndroid(WVHeight,scrollHeight) { //for webview in scrollview.
    console.log("cjf---fGetImgIndexByAndroid WVHeight -- " + WVHeight);
    console.log("cjf---fGetImgIndexByAndroid scrollHeight -- " + scrollHeight);
    console.log("cjf---fGetImgIndexByAndroid window.innerHeight -- " + window.innerHeight);

    var _WVHeight = WVHeight;
    var _scrollH = scrollHeight;
    var _imgUrlArr = [];
    var _imgs = document.getElementsByTagName('img');
    for (var i = 0, len = _imgs.length; i < len; i++) {
        var _value = _imgs[i];
        var _elToTop = _value.offsetTop;
        if (_elToTop - _scrollH - _WVHeight <= 0 && _scrollH < _elToTop + _value.height) {
            if (_value.src == '' || _value.src == 'about:blank') {
                _imgUrlArr.push(i);
            }
        }
    }

    var _imgUrlArrFirst = _imgUrlArr[0];
    var _imgsPrevious = _imgs[_imgUrlArrFirst-1];
    var _imgUrlArrLast = _imgUrlArr[_imgUrlArr.length-1];
    var _imgsNext = _imgs[_imgUrlArrLast + 1];

    if (_imgUrlArrFirst>0 && (_imgsPrevious.src == '' || _imgsPrevious.src == 'about:blank')) {
        _imgUrlArr.unshift(_imgUrlArrFirst-1);
    }

    if (_imgUrlArrLast < (_imgs.length - 1) && (_imgsNext.src == '' || _imgsNext.src == 'about:blank')) {
        _imgUrlArr.push(_imgUrlArrLast+1);
    }

    console.log("cjf---fGetImgIndex length -- " + _imgUrlArr.length);
    window.AndroidJSKit.scrollEndforImage(_imgUrlArr);
}


// 自动替换可视区域img标签src为data_src的值，且图片加载完成后remove占位图。
function fShowImgOnload(WVHeight,scrollHeight) {//for webview in scrollview.
    console.log("cjf---fShowImgOnload WVHeight -- " + WVHeight + " ;scrollHeight="+scrollHeight + " ; innerHeight="+window.innerHeight);

    var _WVHeight = WVHeight;
    var _scrollH = scrollHeight;
    var _imgIndexArr = [];
    var _imgInViewArr = [];
    var i,len;
    var _imgs = document.getElementsByTagName('img');

    for (i = 0, len = _imgs.length; i < len; i++) {
        var _value = _imgs[i];
        var _elToTop = _value.offsetTop;
        if (_elToTop - _scrollH - _WVHeight <= 0 && _scrollH < _elToTop + _value.height) {
            if (_value.src == '' || _value.src == 'about:blank') {
                _imgIndexArr.push(i)
            }
        }
    }
    console.log("cjf---fShowImgOnload _imgIndexArr.length -- " + _imgIndexArr.length);

    var _imgIndexArrFirst = _imgIndexArr[0];
    var _imgsPrevious = _imgs[_imgIndexArrFirst-1];
    var _imgIndexArrLast = _imgIndexArr[_imgIndexArr.length-1];
    var _imgsNext = _imgs[_imgIndexArrLast + 1];

    if (_imgIndexArrFirst>0 && (_imgsPrevious.src == '' || _imgsPrevious.src == 'about:blank')) {
        _imgIndexArr.unshift(_imgIndexArrFirst-1);
    }

    if (_imgIndexArrLast < (_imgs.length - 1) && (_imgsNext.src == '' || _imgsNext.src == 'about:blank')) {
        _imgIndexArr.push(_imgIndexArrLast+1);
    }

    for (i = 0, len = _imgIndexArr.length; i < len; i++) {
        _imgInViewArr.push(_imgs[_imgIndexArr[i]]);
    }

    for (i = 0, len = _imgInViewArr.length; i < len; i++) {
        var _valueInView = _imgInViewArr[i];
        var _src = _valueInView.getAttribute('data_src');
//        _valueInView.src = _src;
        _valueInView.src = _src  + '?' + Math.random();// fix for hongMi note 3s webview not load img after src change.

        _valueInView.onload = function () {
            removePlaceholder(this.id);
        }
    }
}

// 页面加载完成后，将所有img信息传给native。
function fShowImage() {
    console.log("fShowImage()");
    var _imgs = document.getElementsByTagName('img');
    var _arrImgTop = [];
    var _arrImgHeight = [];
    var _arrImgWidth = [];
    for (var i = 0, len = _imgs.length; i < len; i++) {
        var _value = _imgs[i];
        _arrImgTop.push(_value.offsetTop);
        _arrImgHeight.push(_value.height);
        _arrImgWidth.push(_value.width);
    }
    window.AndroidJSKit.getImagePositionInfo(_arrImgTop,_arrImgHeight,_arrImgWidth);
}
