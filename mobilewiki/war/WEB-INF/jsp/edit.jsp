<!DOCTYPE html> 

<html> 

<head>
  <title>${pageName}</title> 
  <meta name="viewport" content="width=device-width, initial-scale=1"> 
  <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css" />
  <script src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
  <script src="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.js"></script>
</head> 

<body> 

<div data-role="page" id="edit">

	<div data-role="header">
      <a href="${cancelUrl}" data-role="button" data-icon="star" data-theme="a" data-rel="back" data-transition="slide">Save</a>
      <a href="${cancelUrl}" data-role="button" data-icon="star" data-theme="a" data-rel="back" data-transition="slide">Cancel</a>         
      <h1>Edit: ${pageName}</h1>
	</div>

	<div data-role="content">
      <form>
		<textarea name="pageText">${pageText}</textarea>
      </form>		
    </div>

</div>

</body>
</html>
