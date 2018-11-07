function createmanga(){
    var manga_request ="true"
    fetchmanga();
}

function updatemanga(){
    var manga_request ="false"
    fetchmanga();
}
  

  function fetchmanga(){
  var form = new FormData();
  var arr = document.getElementsByName("genres[]");
  var checkedarr =[];
  for(i=0; i<arr.length; i++){
       if(arr[i].checked){
           form.append("genres_id",arr[i].value)
       }
  }
  form.append(user_id, localStorage.user_id)
  var name = document.getElementById("manga_name").value
  form.append("manga_name", name)
  var synopsis = document.getElementById("manga_synopsis").value
  form.append("manga_synopsis", synopsis)
  var status = document.getElementById("manga_status").value
  form.append("manga_status", status)
  var file = document.getElementById('file').files
  form.append("file", file)
  form.apped("manga_request", manga_request)
      var init ={
      method: 'POST',
      body: form
  }
  fetch('http://localhost:8080/NitroReader/',init)
  .then(function(res){
      return res.json()
  }).then(function(res){
     
  })
  }