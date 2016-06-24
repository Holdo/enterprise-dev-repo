sap.ui.define([
    'jquery.sap.global',
    'Repository/BaseController',
    'sap/ui/core/Fragment',
    'sap/ui/model/Filter',
    'sap/ui/model/json/JSONModel',
    "sap/m/MessageToast",
    "sap/m/MessageBox"
], function (jQuery, BaseController, Fragment, Filter, JSONModel, MessageToast, MessageBox) {
    "use strict";

    var AppContext = sap.ui.getCore().AppContext;
    var ArtifactController = BaseController.extend("Repository.view.Artifact", {
        onInit: function () {
            this.a = document.createElement("a");
            document.body.appendChild(this.a);
            this.a.style = "display: none";

            this._oTypesGrid = this.getView().byId("typesGrid");

            this.refreshView();

            AppContext.oArtifactController = this;
        },
        onExit: function () {
            //Pls do not leave
        },
        refreshView: function () {
            var that = this;
            var oVersionsSelect = this.getView().byId("versionsSelect");
            this._oTypesGrid.destroyContent();
            this.getView().setModel(AppContext.oArtifactModel);

            if (AppContext.oArtifactModel.getProperty("/attributes")) this._oTypesGrid.addContent(this.createTypeBox("attribute"));
            if (AppContext.oArtifactModel.getProperty("/elements")) this._oTypesGrid.addContent(this.createTypeBox("element"));
            if (AppContext.oArtifactModel.getProperty("/simpleTypes")) this._oTypesGrid.addContent(this.createTypeBox("simpleType"));
            if (AppContext.oArtifactModel.getProperty("/complexTypes")) this._oTypesGrid.addContent(this.createTypeBox("complexType"));
            if (AppContext.oArtifactModel.getProperty("/filters")) this._oTypesGrid.addContent(this.createTypeBox("filter"));
            if (AppContext.oArtifactModel.getProperty("/listeners")) this._oTypesGrid.addContent(this.createTypeBox("listener"));
            if (AppContext.oArtifactModel.getProperty("/operations")) this._oTypesGrid.addContent(this.createTypeBox("operation"));
            if (AppContext.oArtifactModel.getProperty("/requests")) this._oTypesGrid.addContent(this.createTypeBox("request"));
            if (AppContext.oArtifactModel.getProperty("/responses")) this._oTypesGrid.addContent(this.createTypeBox("response"));

            if (AppContext.oArtifactModel.getProperty("/artifactName").endsWith(".war") ||
                AppContext.oArtifactModel.getProperty("/artifactName").endsWith(".WAR")) {
                var ws1 = new WebSocket("ws://" + document.location.host + "/websocket/command/getMetaFile");
                ws1.onopen = function () {
                    var oMessage = {
                        metaFileType : "WEBXML",
                        fullPath : AppContext.oArtifactModel.getProperty("/artifactFullPath").replace(/\\/g, "\\\\"),
                        version : parseInt(AppContext.oArtifactModel.getProperty("/version"))
                    };
                    var sMessage = JSON.stringify(oMessage);
                    console.log("Sending " + sMessage);
                    ws1.send(sMessage);
                };
                ws1.onmessage = function (oEvent) {
                    console.log("Received: " + oEvent.data);
                    var oJSON = JSON.parse(oEvent.data);
                    console.log("WebXML: " + oJSON.fileAsString);
                    that._oTypesGrid.addContent(that.createWebXMLTypeBox(oJSON.fileAsString));
                };
            }

            var ws2 = new WebSocket("ws://" + document.location.host + "/websocket/command/listFileVersions");
            ws2.onopen = function () {
                var oMessage = {
                    fullPath : AppContext.oArtifactModel.getProperty("/artifactFullPath").replace(/\\/g, "\\\\")
                };
                var sMessage = JSON.stringify(oMessage);
                console.log("Sending " + sMessage);
                ws2.send(sMessage);
            };
            ws2.onmessage = function (oEvent) {
                console.log("Received: " + oEvent.data);
                AppContext.oArtifactModel.setProperty("/versions", JSON.parse(oEvent.data));
                oVersionsSelect.setSelectedKey(AppContext.oArtifactModel.getProperty("/version"));
            };
        },
        handleVersionsSelectChange: function (oEvent) {
            var that = this;
            var sVersion = oEvent.getParameter("selectedItem").getKey();
            var ws = new WebSocket("ws://" + document.location.host + "/websocket/command/getArtifactMetadata");
            ws.onopen = function () {
                var oMessage = {
                    fullPath : AppContext.oArtifactModel.getProperty("/artifactFullPath").replace(/\\/g, "\\\\"),
                    version : parseInt(sVersion)
                };
                var sMessage = JSON.stringify(oMessage);
                console.log("Sending " + sMessage);
                ws.send(sMessage);
            };
            ws.onmessage = function (oEvent) {
                console.log("Received: " + oEvent.data);
                AppContext.oArtifactModel.setJSON(oEvent.data, true);
                AppContext.oArtifactModel.setProperty("/version", sVersion);
                that.refreshView();
            };
        },
        handleArtifactDownload: function (oEvent) {
            var that = this;
            var name = AppContext.oArtifactModel.getProperty("/artifactName");
            var fullPath = AppContext.oArtifactModel.getProperty("/artifactFullPath").replace(/\\/g, "\\\\");
            var version = this.getView().byId("versionsSelect").getSelectedKey();
            var ws = new WebSocket("ws://" + document.location.host + "/websocket/binary/download/" + version + "/" + fullPath);
            ws.onopen = function () {
                console.log("Downloading " + fullPath + " of version " + version);
                MessageToast.show("Downloading artifact");
            };
            ws.onmessage = function (oEvent) {
                //Download received blob
                var objectUrl = window.URL.createObjectURL(oEvent.data);
                that.a.href = objectUrl;
                that.a.download = name;
                that.a.click();
                window.URL.revokeObjectURL(objectUrl);
            };
        },
        createTypeBox: function (sTypeName) {
            var oTypeBox = new sap.m.VBox(sTypeName + "TypeBox");
            var oPanel = new sap.m.Panel();
            var oCenteringBox = new sap.m.HBox({justifyContent : "Center"});
            var oText = new sap.m.Text({text : sTypeName.capitalize() + "s"}).addStyleClass("sapMH5FontSize");
            var oList = new sap.m.List({growing : "true", growingThreshold : 3}).bindAggregation(
                "items", "/" + sTypeName + "s",
                new sap.m.StandardListItem({type : "Inactive", title : "{" + sTypeName + "}"}),
                new sap.ui.model.Sorter(sTypeName, false));
            oCenteringBox.addItem(oText);
            oPanel.addContent(oCenteringBox);
            oTypeBox.addItem(oPanel);
            oTypeBox.addItem(oList);

            return oTypeBox;
        },
        createWebXMLTypeBox: function (fileAsString) {
            var oWebXMLHTML = new sap.ui.core.HTML("webXMLHTML", {
                content: "<pre style='white-space: pre-wrap; margin: 0'>" +
                "<code>" + escapeXml(fileAsString) + "</code></pre>"
            });
            oWebXMLHTML.attachAfterRendering(function(){
                console.info("Highlighting XML");
                hljs.initHighlighting.called = false;
                hljs.initHighlighting();
            });

            var oTypeBox = new sap.m.VBox("webXMLTypeBox");
            var oPanel = new sap.m.Panel();
            var oCenteringBox = new sap.m.HBox({justifyContent : "Center"});
            var oText = new sap.m.Text({text : "web.xml"}).addStyleClass("sapMH5FontSize");
            oCenteringBox.addItem(oText);
            oPanel.addContent(oCenteringBox);
            oTypeBox.addItem(oPanel);
            oTypeBox.addItem(oWebXMLHTML);
            oTypeBox.setLayoutData(new sap.ui.layout.GridData({span : "XL12 L12 M12 S12"}));

            return oTypeBox;
        }
    });

    return ArtifactController;
}, /* bExport= */ true);
if (!String.prototype.capitalize) {
    String.prototype.capitalize = function() {
        return this.charAt(0).toUpperCase() + this.slice(1);
    };
}
function escapeXml(unsafe) {
    return unsafe.replace(/[<>&'"]/g, function (c) {
        switch (c) {
            case '<': return '&lt;';
            case '>': return '&gt;';
            case '&': return '&amp;';
            case '\'': return '&apos;';
            case '"': return '&quot;';
        }
    });
}