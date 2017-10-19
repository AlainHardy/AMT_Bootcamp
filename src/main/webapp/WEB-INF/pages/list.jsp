<%@include file="includes/header.jsp" %>

<script>   
   function confirmDelete(id) {      
      if($.cookie('doNotAskMeAgain') === "true") {
         $('#deleteForm-'+id).submit();
      } else {
      $('<div></div>').appendTo('body')
                           .html('<div style="margin-top: 15px; font-weight: bold;">Are you sure you want<br/>to delete this element ?</div><div style="text-align:right"> Do not ask me again <input type="checkbox" id="doNotAskMeAgain"/></div>')
                           .dialog({
                               modal: true,
                               title: 'Confirmation', zIndex: 10000, autoOpen: true,
                               width: 'auto', resizable: false,
                               buttons: {
                                   Ok: function () {
                                      //document.cookie = "doNotAskMeAgain=" + document.getElementById('doNotAskMeAgain').checked;
                                      $.cookie('doNotAskMeAgain',document.getElementById('doNotAskMeAgain').checked);
                                      $('#deleteForm-'+id).submit();
                                      $(this).dialog("close");
                                   },
                               },
                               close: function (event, ui) {
                                   $(this).remove();
                               }
                           });
      }
   }
   
</script>
<header class="masthead">
   <div class="container h-100">            
        <div class="row h-100">           
            <div class="col-lg-12 my-auto">
               <table class="table table-striped">
                  <tr>
                     <th>Brand</th>
                     <th>Color</th>
                     <th>HorsePower</th>
                     <th>Price</th>
                     <th></th>
                     <th></th>
                  </tr>
                  <c:set var="toCenter" value="${'style=\"padding-top:14px\"'}"/>
                  <c:forEach var="car" items="${requestScope.cars}">
                     <tr>
                        <td ${toCenter}>${car[1]}</td>
                        <td ${toCenter}>${car[2]}</td>         
                        <td ${toCenter}>${car[3]}</td>
                        <td ${toCenter}>${car[4]}</td>         
                        <td style="width:1px">
                           <form method="POST"><input type="hidden" name="edit" value="${car[0]}"/><button class="btn" type="submit">Edit</button></form>
                        </td>
                        <td>
                           <form method="POST" id="deleteForm-${car[0]}"><input type="hidden" name="delete" value="${car[0]}"/></form><button class="btn" type="submit" id="btnDelete-${car[0]}" onclick="confirmDelete(${car[0]})">Delete</button>
                        </td>
                     </tr>
                  </c:forEach>
               </table>
               <c:if test="${requestScope.nbOfPages != null}">
               <div class="btn-toolbar" role="toolbar">
                  <div class="btn-group" role="group">
                     <c:choose>
                        <c:when test="${requestScope.pages[0] == requestScope.page}">
                           <span role="button" class="btn btn-primary active"><strong>${requestScope.pages[0]}</strong></span>
                        </c:when>
                        <c:otherwise>
                           <a role="button" class="btn btn-primary" href="list?page=${requestScope.pages[0]}">${requestScope.pages[0]}</a>
                        </c:otherwise>
                     </c:choose>
                     <%-- Display the middle and end pages only if there is more than one page --%>
                     <c:if test="${requestScope.nbOfPages > 1}">
                        <%-- As the first page is always displayed, if the second displayed page is not 
                        the real second page, separate the button to emphasize the ellipsis --%>
                        <c:if test="${requestScope.pages[0] + 1 != requestScope.pages[1]}">
                  </div>
                  <div class="btn-group" role="group">         
                        </c:if>
                        <c:forEach var="page" items="${requestScope.pages}" begin="1" end="${requestScope.nbOfPages-2}">
                           <c:choose>
                              <c:when test="${page == requestScope.page}">
                                 <span role="button" class="btn btn-primary active"><strong>${page}</strong></span>
                              </c:when>
                              <c:otherwise>
                                 <a role="button" class="btn btn-primary" href="list?page=${page}">${page}</a>
                              </c:otherwise>
                           </c:choose>
                        </c:forEach>

                        <%-- Same as for the first page. If the page number before the last one is not 
                        consecutive with the last one, separate the button from the others to show the ellipsis --%>
                        <c:if test="${requestScope.pages[nbOfPages-2] + 1 != requestScope.pages[nbOfPages-1]}">
                           <%--<span>.. - </span>--%>
                  </div>
                  <div class="btn-group" role="group"> 
                        </c:if>
                        <c:choose>
                           <c:when test="${requestScope.pages[nbOfPages-1] == requestScope.page}">
                              <span role="button" class="btn btn-primary active"><strong>${requestScope.pages[nbOfPages-1]}</strong></span>
                           </c:when>
                           <c:otherwise>
                              <a role="button" class="btn btn-primary" href="list?page=${requestScope.pages[nbOfPages-1]}">${requestScope.pages[nbOfPages-1]}</a>
                           </c:otherwise>
                        </c:choose>
                     </c:if>
                  </div>
               </div>
               </c:if>
            </div>
        </div>
   </div>
</header>
         
         
<%@include file="includes/footer.jsp" %>