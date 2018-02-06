<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Wayfinder: OnYourMARC</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.6.1/css/bulma.min.css">
</head>


<style>
  .navbar>.container .navbar-brand,
  .container>.navbar .navbar-brand {
    margin-left: -1rem;
  }

  .navbar>.container .navbar-menu,
  .container>.navbar .navbar-menu {
    margin-right: -1rem;
  }


  .right-table th {
    text-align: right !important;
    vertical-align: middle;
  }

  .right-table td {
    text-align: right !important;
    vertical-align: middle;
  }
</style>


<body class="layout-default">




  <!-- NAVIGATION -->


  <nav class="navbar">
    <div class="container">
      <div class="navbar-brand">
        <a class="navbar-item" href="#">
              <img src="img/logo_smaller.png" alt="Wayfinder: Workflows for librarians">
            </a>

        <a class="navbar-item is-hidden-desktop" href="https://github.com/jgthms/bulma" target="_blank">
                <span class="icon" style="color: #333;">
                  <i class="fa fa-lg fa-github"></i>
                </span>
              </a>

        <a class="navbar-item is-hidden-desktop" href="https://twitter.com/jgthms" target="_blank">
                <span class="icon" style="color: #55acee;">
                  <i class="fa fa-lg fa-twitter"></i>
                </span>
              </a>

        <div class="navbar-burger burger" data-target="navMenuIndex">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>



      <div id="navMenuIndex" class="navbar-menu">
        <div class="navbar-start">
          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link  " href="#">
            Workflows
          </a>
            <div class="navbar-dropdown is-boxed">
              <a class="navbar-item selected" href="/process?id=2">
              Patron Requests
            </a>
              <a class="navbar-item " href="#">
              LMC Requests
            </a>

            </div>
          </div>


          <div class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link " href="https://bulma.io/blog/">
          Blog
        </a>
            <div id="blogDropdown" class="navbar-dropdown is-boxed" data-style="width: 18rem;">

              <a class="navbar-item" href="/2017/10/09/roses-are-red-links-are-blue/">
                <div class="navbar-content">
                  <p>
                    <small class="has-text-link">09 Oct 2017</small>
                  </p>
                  <p>Roses are red – Links are blue</p>
                </div>
              </a>

              <a class="navbar-item" href="/2017/08/03/list-of-tags/">
                <div class="navbar-content">
                  <p>
                    <small class="has-text-link">03 Aug 2017</small>
                  </p>
                  <p>New feature: list of tags</p>
                </div>
              </a>

              <a class="navbar-item" href="/2017/08/01/bulma-bootstrap-comparison/">
                <div class="navbar-content">
                  <p>
                    <small class="has-text-link">01 Aug 2017</small>
                  </p>
                  <p>Bulma / Bootstrap comparison</p>
                </div>
              </a>

              <a class="navbar-item" href="https://bulma.io/blog/">
            More posts
          </a>
              <hr class="navbar-divider">
              <div class="navbar-item">
                <div class="navbar-content">
                  <div class="level is-mobile">
                    <div class="level-left">
                      <div class="level-item">
                        <strong>Stay up to date!</strong>
                      </div>
                    </div>
                    <div class="level-right">
                      <div class="level-item">
                        <a class="button bd-is-rss is-small" href="https://bulma.io/atom.xml">
                      <span class="icon is-small">
                        <i class="fa fa-rss"></i>
                      </span>
                      <span>Subscribe</span>
                    </a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>


          <div class="navbar-item has-dropdown is-hoverable">
            <div class="navbar-link">
              More
            </div>
            <div id="moreDropdown" class="navbar-dropdown is-boxed">
              <a class="navbar-item " href="https://bulma.io/bulma-start/">
                <p>
                  <strong>Bulma start</strong>
                  <br>
                  <small>A tiny npm package to get started</small>
                </p>
              </a>
              <hr class="navbar-divider">
              <a class="navbar-item " href="https://bulma.io/made-with-bulma/">
                <p>
                  <strong>Made with Bulma</strong>
                  <br>
                  <small>The official community badge</small>
                </p>
              </a>
              <hr class="navbar-divider">
              <a class="navbar-item " href="https://bulma.io/extensions/">
                <p>
                  <strong>Extensions</strong>
                  <br>
                  <small>Side projects to enhance Bulma</small>
                </p>
              </a>
            </div>
          </div>

        </div>
        <!--  END NAVBAR START -->

        <div class="navbar-end">

          <div class="navbar-item">
            <div class="field is-grouped">
              <p class="control">


              </p>
              <p class="control">
                <a class="button is-primary is-info" href="#">
              <span class="icon">
                <i class="fa fa-window-close"></i>
              </span>
              <span>Logout</span>
            </a>
              </p>
            </div>
          </div>
        </div>
        <!-- END NAVBAR END-->


      </div>
      <!-- END NAVBAR MENU -->

    </div>
    <!-- end container -->
  </nav>


  <section class="section">
    <div class="container">


      <h1 class="title is-2">OnYourMARC</h1>
      <h1 class="subtitle is-3">Find the best MARC record</h1>
      
      
      <div id="errormsgcol" class="columns is-hidden">
  			<div class="column is-three-fifths">
 			<div id="errormsg" class="notification is-danger is-hidden"></div>
			<span class="payment-errors is-hidden"></span>
 		 	</div>
        	<div class="column is-one-fifths">
      		</div>
  	  </div>
  		

    </div>
  </section>


  <div class="container">
    <div class="columns">

      <div class="column">

		<form action="marc" method="post" enctype="multipart/form-data">
        <div class="box">
        <nav class="level">
          <h1 class="title">Upload file (Excel or MARC):</h1>
          <div class="level-right"><a href="#" id="oneRecord" onclick="$('#errormsg').addClass('is-hidden');openFormModal('oneRecordLookupModal')">Lookup One Record</a></div>
        </nav>
          <div class="file has-name is-fullwidth">
           
            <label class="file-label">
                        <input class="file-input" type="file" name="file" id="file" onchange="displayFileName()">
                        <span class="file-cta">
                          <span class="file-icon">
                            <i class="fa fa-upload"></i>
                          </span>
                          <span class="file-label">
                            Choose a file
                          </span>
                        </span>
                        <span class="file-name" id="filename">

                        </span>


                      </label>
          </div>
          <br><br>
          <span>
                      <input class="button is-info is-medium" type="submit" value="upload file">
                    </span>
          <br><br>
          
        </div>
        </form>

      </div>

    </div>

  </div>




<!--  MODAL FOR SINGLE RECORD LOOKUP -->
<jsp:include page="lookupOneRecordForm.jsp"/>


<script>
	
	//TODO PUT THESE FUNCTIONS IN ONE JS FILE - THEY ARE REPEATED
	
	function closeFormModal(e,elementid) {
	
		  e.preventDefault();
		  var html = document.documentElement;
		  var target = document.getElementById(elementid);
		  target.classList.remove('is-active');
		  html.classList.remove('is-clipped');
	}

	function openFormModal(elementid) {
	  var html = document.documentElement;
	  var target = document.getElementById(elementid);
	  target.classList.add('is-active');
	  html.classList.add('is-clipped');
	}

</script>



</body>


<script>
  document.addEventListener('DOMContentLoaded', function() {

    // Get all "navbar-burger" elements
    var $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

    // Check if there are any navbar burgers
    if ($navbarBurgers.length > 0) {

      // Add a click event on each of them
      $navbarBurgers.forEach(function($el) {
        $el.addEventListener('click', function() {

          // Get the target from the "data-target" attribute
          var target = $el.dataset.target;
          var $target = document.getElementById(target);

          // Toggle the class on both the "navbar-burger" and the "navbar-menu"
          $el.classList.toggle('is-active');
          $target.classList.toggle('is-active');

        });
      });
    }

  });
</script>

  <script>
    function displayFileName() {
      var file = document.getElementById("file");

      if (file.files.length > 0) {
        document.getElementById('filename').innerHTML = file.files[0].name;
      }

    }
  </script>

</html>
