UCLA CS 144
Fall 2012
Project 4
Mark Vismonte
Logan Chang
11/26/2012

Project Part 4
=====================
We implemented the website using KnockoutJS and Twitter Bootstrap. The initial
web page has a search bar in the upper right hand corner where users can input
a search query. The search results are displayed in a column on the left side
of the page as a list of links. When the user clicks on a link, the item
details are displayed on the right side of the page.

Instead of using server-side templating, we use knockout.js for client-side
templates.  We have multiple endpoints for the /ebay/search URI.  One endpoint
simply renders the HTML page and the other actually performs the search and
returns them in JSON form (when content-type is set to 'application/json').

We also use JS to do pagination, so that we don't have to reload the page
everytime we want to get new results.  One downside to this approach is
we don't have a dedicated URI for item.  An item is only viewed next to
the search results.

We implemented autocomplete by showing possible text, but we didn't implement
the autocomplete drop down because we believed that it interfered with our
design of the search box on the top of each page.