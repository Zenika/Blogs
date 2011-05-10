var color="#053c7b";
(function() {
    var head = document.head || document.getElementsByTagName('head')[0],
        firstLink = document.getElementsByTagName('link')[0],
        userAgent = window.navigator.userAgent,
        cssPath = "sencha-touch/",
        isBB = userAgent.search(/blackberry/i) !== -1,
        isAndroid =  userAgent.search(/Android/i) !==-1,
        isIphone =  userAgent.search(/iPhone/i) !==-1,
        link = document.createElement('link');

    link.type = 'text/css';
    link.rel = 'stylesheet';
     
    
    if (isBB) {
        link.href = cssPath + "bb6.css";
    }else if(isAndroid) {
		link.href = cssPath + "android.css";
		color="#f87d00";
	} else if(isIphone){
		link.href = cssPath + "apple.css";
		color="#4c91ea";
	} else {
        link.href = cssPath + "sencha-touch.css";
    }
    if (firstLink) {
        head.insertBefore(link, firstLink);
    } else {
        head.appendChild(link);
    }
})();
