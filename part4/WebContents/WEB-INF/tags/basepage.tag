<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>
<%@attribute name="extraScripts" fragment="true" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title><jsp:invoke fragment="title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="/eBay/css/bootstrap.min.css" rel="stylesheet">
    <link href="/eBay/css/eBay.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="/eBay/css/bootstrap-responsive.min.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="shortcut icon" href="/favicon.ico">
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="/eBay">CS144 eBay</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="/eBay">Home</a></li>
              <li><a href="/eBay/search">Search</a></li>
            </ul>

            <form action="/eBay/search" method="get" id="search-form" class="pull-right">
              <input type="text" placeholder="Search eBay…" name="q" class="input-medium search-query">
              <input type="hidden" name="numResultsToSkip" value="0">
              <input type="hidden" name="numResultsToReturn" value="50">
            </form>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
      <jsp:doBody/>
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="/eBay/js/jQuery.js"></script>
    <script src="/eBay/js/bootstrap.min.js"></script>
    <script src="/eBay/js/knockout-2.2.0.js"></script>
    <script src="/eBay/js/knockout-2.2.0.js"></script>
    <jsp:invoke fragment="extraScripts"/>
  </body>
</html>
