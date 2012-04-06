<!DOCTYPE html> 
<html> 

<head>
  <title>${pageName}</title> 
  <meta name="viewport" content="width=device-width, initial-scale=1"> 
  <link rel="stylesheet" href="/jquery.mobile-1.0.1.min.css" />
  <script src="/jquery-1.6.4.min.js"></script>
  <script>
    $(document).bind('pageinit',function() {
      $('#saveControl').click(function() { 
        $('#editForm').submit(); 
      });
    });
  </script>
  <script src="/jquery.mobile-1.0.1.min.js"></script>
</head> 

<body> 

<div data-role="page" id="wiki">
  <div data-role="header">
    <h1>${pageName}</h1>
      <% if (request.getAttribute("editUrl") != null) { %>
        <a href="${editUrl}" data-role="button" data-icon="star" data-theme="a" data-transition="slide">Edit</a>
        <a href="/help" data-role="button" data-icon="star" data-theme="a" data-transition="slide">Help</a>
      <% } %>
	</div>
	<div data-role="content">
      <div id="pageText">${pageText}</div>
	</div>
	<div data-role="footer">
      <% if (request.getAttribute("homeUrl") != null) { %>
        <a href="${homeUrl}" data-role="button" data-icon="star" data-theme="a">Home</a>
        <a href="${logoutUrl}" data-role="button" data-icon="star" data-theme="a" data-ajax="false">Logout</a>
      <% } else { %>
        <a href="${loginUrl}" data-role="button" data-icon="star" data-theme="a" data-ajax="false">Login</a>
      <% } %>
	</div>
</div>

</body>
</html>