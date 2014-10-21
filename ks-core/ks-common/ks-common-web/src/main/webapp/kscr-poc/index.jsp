<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" ng-app="kscrPocApp"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" ng-app="kscrPocApp"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" ng-app="kscrPocApp"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" ng-app="kscrPocApp"> <!--<![endif]-->
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title ng-bind-html="pageTitle"></title>
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">
  <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

  <link rel="stylesheet" href="styles/b08f5f5c.main.css">

</head>
<body>
  <!--[if lt IE 7]>
    <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
  <![endif]-->

  <!--[if lt IE 9]>
  <script src="scripts/4d08494f.ie.js"></script>
  <![endif]-->

  <!-- Add your site or application content here -->
  <div ui-view=""></div>

  <script src="scripts/c44047f9.vendor.js"></script>

  <script src="scripts/1856abcb.modules.js"></script>

<script>'use strict';angular.module('kscrPocApp').value('configServer', {apiBase: '${ConfigProperties.application.url}/services/',appUrl: '${ConfigProperties.application.url}/'});</script></body>
</html>