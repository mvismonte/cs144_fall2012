<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title><%= request.getAttribute("title") %></title>
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
              <li class="active"><a href="/">Home</a></li>
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
      <h1>eBay - RCA Direct TV Satellite Receiver w/ H Card</h1>
      <dl class="dl-horizontal">
        <dt>Categories</dt>
        <dd>Cable &amp; Satellite, Consumer Electronics, DSS Receivers, Home Audio &amp; Video, Satellite</dd>
        <dt>Currently</dt>
        <dd>$125.00</dd>
        <dt>First Bid</dt>
        <dd>$9.99</dd>
        <dt>Location</dt>
        <dd>Pinckney</dd>
        <dt>Country</dt>
        <dd>USA</dd>
        <dt>Started</dt>
        <dd>Dec-09-01 18:30:21</dd>
        <dt>Ends</dt>
        <dd>Dec-16-01 18:30:21</dd>
        <dt>Seller</dt>
        <dd>murdock5511</dd>
        <dt>Description</dt>
        <dd>You are bidding on a RCA Direct TV Satelite receiver and H Card. The model is DRD420RE and it will come with an H card and a user guide. When hooked to a TV (without dish) it reads: searching for satellite signal, pleaes stand by. This is my satellite equipment and was working 2 weeks ago before I cancelled my subscription. It is sold as is. Shipping will be $14. Free Honesty Counters powered by Andale! Payment Details See item description and Payment Instructions, or contact seller for more information. Payment Instructions See item description or contact seller for more information.</dd>
      </dl>

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="/eBay/js/jQuery.js"></script>
    <script src="/eBay/js/bootstrap.min.js"></script>
    <script src="/eBay/js/knockout-2.2.0.js"></script>

  </body>
</html>
