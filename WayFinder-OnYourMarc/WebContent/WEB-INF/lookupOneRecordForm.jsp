<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous">
</script>

<div id="oneRecordLookupModal" class="modal">
    <div class="modal-background"></div>
	  <div class="modal-card">
	    <header class="modal-card-head">
	      <p class="modal-card-title">Create Patron Request</p>
	      <button class="delete"  onclick="closeFormModal(event,'oneRecordLookupModal')"></button>
	    </header>
	    <section class="modal-card-body">
	      <div class="content">
	        <span id="formholder">
	        <h1>Some Instructions</h1>
	        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla accumsan, 
	        metus ultrices eleifend gravida, nulla nunc varius lectus, nec rutrum justo nibh eu lectus. 
	        Ut vulputate semper dui. Fusce erat odio, sollicitudin vel erat vel, interdum mattis neque.</p>
	        
	        <form name="marclookupform" id="marclookupform">
	            <!-- TODO: THESE HIDDEN VALUES SHOULD BE DYNAMIC -->
	        	<div class="field">
				  <label class="label">ISBN</label>
				  <div class="control">
				    <input class="input" name="isbn" type="text" placeholder="" value="9781610917902">
				  </div>
				</div>
		        <div class="field">
				  <label class="label">OCLC</label>
				  <div class="control">
				    <input class="input" name="oclc" type="text" placeholder="" value="">
				  </div>
				</div>
				<div class="field">
				  <label class="label">Price</label>
				  <div class="control">
				    <input class="input" name="price" type="text" placeholder="" value="5.55">
				  </div>
				</div>
				<div class="field">
				  <label class="label">Fund Code</label>
				  <div class="control">
				    <input class="input" name="fundCode" type="text" placeholder="" value="BART">
				  </div>
				</div>
				<div class="field">
				  <label class="label">Object</label>
				  <div class="control">
				    <input class="input" name="object" type="text" placeholder="" value="PRDA">
				  </div>
				</div>
				<div class="field">
				  <label class="label">Sub-Object</label>
				  <div class="control">
				    <input class="input" name="subObject" type="text" placeholder="" value="GIST">
				  </div>
				</div>
			</form>  
			</span>
	      </div>
	    </section>
	    <footer class="modal-card-foot">
	     <span id="footerspan">
	      <a class="button is-success" onclick="lookupMarc()">Get MARC Record!</a>
	      <a class="button" onclick="closeFormModal(event,'oneRecordLookupModal')">Cancel</a>
	      </span>
	    </footer>
	  </div>
 </div>
 
 
 <script>
//TODO PUT THESE FUNCTIONS IN ONE JS FILE - THEY ARE REPEATED
function lookupMarc() {
	   $('#errormsg').addClass('is-hidden');
	   var formData = $("#marclookupform").serialize();
	   $.ajax({
	        type: "POST",
	        dataType: 'text',
	        data: formData,
	        url: "/service/marcservice/singleMarcRecord",
	        success: requestSuccessful,
	        error: problem
	     });
}

function problem(response) {
	//TODO IMPROVE AND PUT IN JUST ONE PLACE
	$('#errormsg').html(response.responseText + "  <a href='/login'>Login page</a>");
	$('#errormsg').addClass('is-danger');
	$('#errormsg').removeClass('is-hidden');
	$('#errormsgcol').removeClass('is-hidden');
	
}

function requestSuccessful(response) {
	closeFormModal(event,'oneRecordLookupModal');
	var displayString = "<a href='"+ response +"'>download your file here</a>" ;
	$('#errormsg').html(displayString);
	$('#errormsg').removeClass('is-danger');
	$('#errormsg').addClass('is-primary');
	$('#errormsg').removeClass('is-hidden');
	$('#errormsgcol').removeClass('is-hidden');

}



</script>
 
 
