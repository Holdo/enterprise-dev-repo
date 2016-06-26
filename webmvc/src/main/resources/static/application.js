oApplication = { // Application is an object

    views: {}, // Application views

    load: function () {
        var opts = {
            length: 20, // The length of each line
            width: 6, // The line thickness
            radius: 24, // The radius of the inner circle
            shadow: true, // Whether to render a shadow
            hwaccel: true // Whether to use hardware acceleration
        };
        this.spinner = new Spinner(opts).spin(document.getElementById("spinner"));

        var i = 0;
        setTimeout(function () {
            oApplication.loadSAPUI5();
        }, 500);
        var smoothBgInterval = setInterval(function () {
            document.body.style.backgroundImage = "linear-gradient(to bottom,rgba(45, 169, 177, " + i + ") 0,rgba(45, 169, 177, " + i + ") 50%,rgba(26, 76, 125, " + i + ") 100%)";
            i += 0.016;
            if (i >= 1) {
                document.body.style.backgroundImage = "linear-gradient(to bottom,rgba(45, 169, 177, 1) 0,rgba(45, 169, 177, 1) 50%,rgba(26, 76, 125, 1) 100%)";
                clearInterval(smoothBgInterval);
            }
        }, 0.016);
    },

    loadSAPUI5: function () {
        var s, r, t;
        r = false;
        s = document.createElement('script');
        s.type = "text/javascript";
        s.src = "https://openui5.hana.ondemand.com/resources/sap-ui-core.js";
        s.id = "sap-ui-bootstrap";
        s.setAttribute("data-sap-ui-language", "en");
        s.setAttribute("data-sap-ui-libs", "sap.m");
        s.setAttribute("data-sap-ui-compatVersion", "edge");
        s.setAttribute("data-sap-ui-theme", "sap_bluecrystal");
        s.setAttribute("data-sap-ui-xx-bindingSyntax", "complex");
        s.setAttribute("data-sap-ui-resourceroots", '{"Repository": "./repository"}');
        s.setAttribute("data-sap-ui-preload", "sync");
        s.onload = s.onreadystatechange = function () {
            //console.log( this.readyState ); //uncomment this line to see which ready states are called.
            if (!r && (!this.readyState || this.readyState == 'complete')) {
                r = true;
                oApplication.onSAPUI5Loaded();
            }
        };
        t = document.getElementsByTagName('script')[0];
        t.parentElement.insertBefore(s, t);
    },

    onSAPUI5Loaded: function () {
        oApplication.initializeUI5();
        var body = $("body");
        body.fadeOut("slow", function () {
            var spinner = $("#spinner");
            spinner.empty();
            spinner.removeAttr("style");
            //DONE, Spinner is destroyed and App controller will fade in after rendering
        });

        var sCss = "{background-image:linear-gradient(to bottom,rgba(45, 169, 177, 1) 0,rgba(45, 169, 177, 1) 50%,rgba(26, 76, 125, 1) 100%)!important;}";
        var style = document.createElement("style");
        style.appendChild(document.createTextNode(".sapMGlobalBackgroundColor" + sCss));
        style.appendChild(document.createTextNode(".sapUiGlobalBackgroundColor" + sCss));
        style.appendChild(document.createTextNode(".sapUiBody" + sCss));
        style.appendChild(document.createTextNode(".sapUiUfdShellSearch" + "{width:auto!important;max-width:inherit!important;display:inherit!important;}"));
        //style.appendChild(document.createTextNode(".sapUiUfdShellHeadCenter" + "{min-width:29.375em!important;}"));
        document.head.appendChild(style);
        oApplication.app.placeAt("content");
    },

    initializeUI5: function () {
        sap.ui.localResources("repository");

        this.app = new sap.ui.core.ComponentContainer({
            //height : "99.68%", //workaround for Chrome scrollbar issue
            name: "Repository",
            height: "100%"
        });
    }
};