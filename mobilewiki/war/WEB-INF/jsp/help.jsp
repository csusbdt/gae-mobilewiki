<!DOCTYPE html> 

<html> 

<head>
  <title>${pageName}</title> 
  <meta name="viewport" content="width=device-width, initial-scale=1"> 
  <link rel="stylesheet" href="/jquery.mobile-1.0.1.min.css" />
  <script src="/jquery-1.6.4.min.js"></script>
  <script src="/jquery.mobile-1.0.1.min.js"></script>
</head> 

<body> 

<div data-role="page" id="help">
	<div data-role="header">
      <a href="#" data-rel="back" data-role="button" data-inline="true" data-icon="back">OK</a>
      <h1>Help</h1>
	</div>
	<div data-role="content">
      <p>
        To link to one of your pages, enclose phrase in double brackets.
        For example, [[home]] creates a link to your home page.
      </p>
      <p>
        To link to someone else's page, preceed the phrase with </b>nickname:</b>.
        For example, [[csusbdt:home]] creates a link to the home page of csusbdt.
      </p>
      <p>
        Note: IE doesn't seem to not properly display pre-existing new lines inside textarea elements, 
        but they are still there.  For this reason, you will not see your pre-existing newlines when you
        edit a page in IE.
      </p>
	</div>
</div>

</body>
</html>