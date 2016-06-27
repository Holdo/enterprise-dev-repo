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
    
    var ArtifactController = BaseController.extend("Repository.view.Artifact", {
        onInit: function () {
            this.a = document.createElement("a");
            document.body.appendChild(this.a);
            this.a.style.cssText = "display: none";

            this._oTypesGrid = this.getView().byId("typesGrid");
            this.getView().setModel(new JSONModel({}));

            this.getRouter().getRoute("artifact").attachPatternMatched(this._onObjectMatched, this);
        },
        onExit: function () {
            //Pls do not leave
        },
        _onObjectMatched: function (oEvent) {
            var sURLPath = decodeURIComponent(oEvent.getParameter("arguments").fullPath);
            if (sURLPath == "") {
                console.error("ERROR: Matched empty path");
                return;
            }
            console.info("Matched fullPath " + sURLPath);

            var sName;
            var sPath;
            var sFullPath = sURLPath;

            if (!sURLPath.includes("/")) {
                sPath = "";
                sName = sURLPath;
            } else {
                var iLastSlashIndex = sURLPath.lastIndexOf("/");
                sPath = sURLPath.substring(0, iLastSlashIndex);
                sName = sURLPath.substring(iLastSlashIndex + 1);
            }

            var that = this;
            var ws = new WebSocket("ws://" + document.location.host + "/websocket/command/getArtifactMetadata");
            ws.onopen = function () {
                var oMessage = {
                    fullPath : sFullPath,
                    version : 0
                };
                var sMessage = JSON.stringify(oMessage);
                console.log("Sending " + sMessage);
                ws.send(sMessage);
            };
            ws.onmessage = function (oEvent) {
                console.log("Received: " + oEvent.data);
                var oModel = new JSONModel(JSON.parse(oEvent.data));
                oModel.setProperty("/artifactName", sName);
                oModel.setProperty("/artifactPath", sPath);
                oModel.setProperty("/artifactFullPath", sFullPath);
                oModel.setProperty("/version", 0);
                that.getView().setModel(oModel);
                that.refreshView();
            };
        },
        refreshView: function () {
            var that = this;
            var oVersionsSelect = this.getView().byId("versionsSelect");
            this._oTypesGrid.destroyContent();
            
            var oViewModel = this.getView().getModel();
            
            if (oViewModel.getProperty("/artifactPath") == "") this.getView().byId("artifactPathText").setText("root folder");

            var bCompact;
            if (oViewModel.getProperty("/elements")) {
                bCompact = !!(oViewModel.getProperty("/complexTypes").length > 6 ||
                oViewModel.getProperty("/simpleTypes").length > 6 ||
                oViewModel.getProperty("/attributes").length > 6 ||
                oViewModel.getProperty("/elements").length > 6);
                //Create boxes
                if (oViewModel.getProperty("/complexTypes")) this._oTypesGrid.addContent(this.createTypeBox("complexType", bCompact));
                if (oViewModel.getProperty("/simpleTypes")) this._oTypesGrid.addContent(this.createTypeBox("simpleType", bCompact));
                if (oViewModel.getProperty("/attributes")) this._oTypesGrid.addContent(this.createNameParentTypeBox("attribute", bCompact));
                if (oViewModel.getProperty("/elements")) this._oTypesGrid.addContent(this.createNameParentTypeBox("element", bCompact));
            } else if (oViewModel.getProperty("/listeners")) {
                bCompact = !!(oViewModel.getProperty("/filters").length > 6 ||
                oViewModel.getProperty("/listeners").length > 6);
                //Create boxes
                if (oViewModel.getProperty("/filters"))     this._oTypesGrid.addContent(this.createTypeBox("filter", bCompact));
                if (oViewModel.getProperty("/listeners"))     this._oTypesGrid.addContent(this.createTypeBox("listener", bCompact));
            } else if (oViewModel.getProperty("/operations")) {
                bCompact = !!(oViewModel.getProperty("/operations").length > 6 ||
                oViewModel.getProperty("/requests").length > 6 ||
                oViewModel.getProperty("/responses").length > 6);
                //Create boxes
                if (oViewModel.getProperty("/operations")) this._oTypesGrid.addContent(this.createTypeBox("operation", bCompact));
                if (oViewModel.getProperty("/requests")) this._oTypesGrid.addContent(this.createNameParentTypeBox("request", bCompact));
                if (oViewModel.getProperty("/responses")) this._oTypesGrid.addContent(this.createNameParentTypeBox("response", bCompact));
            }

            var iContentSize = this._oTypesGrid.getContent().length;
            if (iContentSize == 1) {
                this._oTypesGrid.setDefaultSpan("XL12 L12 M12 S12");
            } else if (iContentSize == 2) {
                this._oTypesGrid.setDefaultSpan("XL6 L6 M6 S12");
            } else if (iContentSize == 3) {
                this._oTypesGrid.setDefaultSpan("XL4 L4 M6 S12");
            } else {
                this._oTypesGrid.setDefaultSpan("XL3 L4 M6 S12");
            }

            if (oViewModel.getProperty("/artifactName").endsWith(".war") ||
                oViewModel.getProperty("/artifactName").endsWith(".WAR")) {
                var ws1 = new WebSocket("ws://" + document.location.host + "/websocket/command/getMetaFile");
                ws1.onopen = function () {
                    var oMessage = {
                        metaFileType : "WEBXML",
                        fullPath : oViewModel.getProperty("/artifactFullPath"),
                        version : parseInt(oViewModel.getProperty("/version"))
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
                    fullPath : oViewModel.getProperty("/artifactFullPath")
                };
                var sMessage = JSON.stringify(oMessage);
                console.log("Sending " + sMessage);
                ws2.send(sMessage);
            };
            ws2.onmessage = function (oEvent) {
                console.log("Received: " + oEvent.data);
                var aVersions = JSON.parse(oEvent.data);
                oViewModel.setProperty("/versions", aVersions);
                if (oViewModel.getProperty("/version") == 0) oVersionsSelect.setSelectedKey(aVersions[0]);
                else oVersionsSelect.setSelectedKey(oViewModel.getProperty("/version"));
            };
        },
        handleVersionsSelectChange: function (oEvent) {
            var that = this;
            var oViewModel = this.getView().getModel();
            var sVersion = oEvent.getParameter("selectedItem").getKey();
            var ws = new WebSocket("ws://" + document.location.host + "/websocket/command/getArtifactMetadata");
            ws.onopen = function () {
                var oMessage = {
                    fullPath : oViewModel.getProperty("/artifactFullPath"),
                    version : parseInt(sVersion)
                };
                var sMessage = JSON.stringify(oMessage);
                console.log("Sending " + sMessage);
                ws.send(sMessage);
            };
            ws.onmessage = function (oEvent) {
                console.log("Received: " + oEvent.data);
                oViewModel.setJSON(oEvent.data, true);
                oViewModel.setProperty("/version", sVersion);
                that.refreshView();
            };
        },
        handleArtifactDownload: function (oEvent) {
            var that = this;
            var oViewModel = this.getView().getModel();
            var name = oViewModel.getProperty("/artifactName");
            var fullPath = oViewModel.getProperty("/artifactFullPath");
            var version = this.getView().byId("versionsSelect").getSelectedKey();
            var ws = new WebSocket("ws://" + document.location.host + "/websocket/binary/download/" + version + "/" + fullPath);
            ws.onopen = function () {
                console.log("Downloading " + fullPath + " of version " + version);
                MessageToast.show("Downloading artifact");
            };
            ws.onmessage = function (oEvent) {
                //Download received blob
                if (navigator.appVersion.toString().indexOf('.NET') > 0) {
                    window.navigator.msSaveBlob(oEvent.data, name);
                } else {
                    var objectUrl = window.URL.createObjectURL(oEvent.data);
                    that.a.href = objectUrl;
                    that.a.download = name;
                    that.a.click();
                    setTimeout(function(){
                        window.URL.revokeObjectURL(objectUrl);
                    }, 100);
                }
            };
        },
        createTypeBox: function (sTypeName, bCompact) {
            var oTypeBox = new sap.m.VBox(sTypeName + "TypeBox");
            var oPanel = new sap.m.Panel();
            var oCenteringBox = new sap.m.HBox({justifyContent : "Center"});
            var oText = new sap.m.Text({text : sTypeName.capitalize() + "s"}).addStyleClass("sapMH5FontSize");
            var oList = new sap.m.List({growing : "true", growingThreshold : 6}).bindAggregation(
                "items", "/" + sTypeName + "s",
                new sap.m.StandardListItem({type : "Inactive", title : "{" + sTypeName + "}"}),
                new sap.ui.model.Sorter(sTypeName, false));
            if (bCompact) oList.addStyleClass("sapUiSizeCompact");
            oCenteringBox.addItem(oText);
            oPanel.addContent(oCenteringBox);
            oTypeBox.addItem(oPanel);
            oTypeBox.addItem(oList);

            return oTypeBox;
        },
        createNameParentTypeBox: function (sTypeName, bCompact) {
            var oTypeBox = new sap.m.VBox(sTypeName + "TypeBox");
            var oPanel = new sap.m.Panel();
            var oCenteringBox = new sap.m.HBox({justifyContent : "Center"});
            var oText = new sap.m.Text({text : sTypeName.capitalize() + "s"}).addStyleClass("sapMH5FontSize");
            var oList = new sap.m.List({growing : "true", growingThreshold : 6}).bindAggregation(
                "items", "/" + sTypeName + "s",
                new sap.m.StandardListItem({type : "Inactive", title : "{name}", info : "{parent}", infoState : sap.ui.core.ValueState.Warning}),
                [new sap.ui.model.Sorter("name", false), new sap.ui.model.Sorter("parent", false)]);
            if (bCompact) oList.addStyleClass("sapUiSizeCompact");
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
if (!String.prototype.includes) {
    String.prototype.includes = function(search, start) {
        'use strict';
        if (typeof start !== 'number') {
            start = 0;
        }

        if (start + search.length > this.length) {
            return false;
        } else {
            return this.indexOf(search, start) !== -1;
        }
    };
}
if (!String.prototype.endsWith) {
    String.prototype.endsWith = function(searchString, position) {
        var subjectString = this.toString();
        if (typeof position !== 'number' || !isFinite(position) || Math.floor(position) !== position || position > subjectString.length) {
            position = subjectString.length;
        }
        position -= searchString.length;
        var lastIndex = subjectString.indexOf(searchString, position);
        return lastIndex !== -1 && lastIndex === position;
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