sap.ui.define([
    "sap/ui/core/UIComponent"
], function (UIComponent) {
    "use strict";
    return UIComponent.extend("RootFolder.repository.Component", {

        metadata: {
            manifest: "json"
        },

        init : function () {
            UIComponent.prototype.init.apply(this, arguments);
            this.getTargets().display("home");
        }
    });
}, /* bExport= */ true);