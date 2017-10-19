<%@include file="includes/header.jsp" %>
<header class="masthead">
   <div class="container h-100">            
        <div class="row h-100">           
            <div class="col-lg-10 my-auto">
               <c:if test="${requestScope.errorMessage != null}">
                  <div class="alert alert-danger">
                     ${requestScope.errorMessage}
                  </div>
               </c:if>
               <form class="form-vertical" method="POST">
                  <div class="form-group">
                     <label class="control-label">Number of cars to generate</label>
                     <input type="text" class="form-control" id="qt" name="quantity"/>
                  </div>
                  <div class="form-group">
                     <button type="default" class="btn btn-default" onclick="displayMessage()">Generate random</button>
                  </div>
               </form>
            </div>
        </div>
   </div>
</header>

<script> 
   function displayMessage() {
      if(isNaN(document.getElementById('qt').value) === false && document.getElementById('qt').value !== "") {
         document.getElementsByTagName('button').disabled = false;
         var text = document.createElement('span');
         text.innerHTML = "<h1>Insert in progress</h1><h2>Please wait ...</h2>";
         text.style.cssText = "position:fixed;text-align:center;width:100%;z-index:11;top:31%;";
         var myDiv = document.createElement('div');
         myDiv.style.cssText = "height:100%;width:100%;position:fixed;background-color:white;z-index:10;opacity:.8;top:0px;";
         document.body.appendChild(text);
         document.body.appendChild(myDiv);
      }
   }
</script>

<%@include file="includes/footer.jsp" %>