
window.onscroll = function() {stickyFunc()};

var nav = document.getElementById("myNav");
var sticky = nav.offsetTop;

function stickyFunc() {
  if (window.pageYOffset >= sticky) {
    nav.classList.add("sticky");
  } else {
    nav.classList.remove("sticky");
  }
}
///////////////////////////////////////

var button=document.getElementById("darkModeButton");
darkOn = localStorage.getItem("dark") == "true" ? true : false;
setTheme();

function setTheme(){
localStorage.setItem("dark", darkOn ? "true" : "false");

if(darkOn){
document.body.setAttribute("theme","dark");
button.innerHTML="Light";
}
else{
document.body.setAttribute("theme","light");
button.innerHTML="Dark";
}
}

var darkOn=false;
function toggle(){
darkOn=!darkOn;
setTheme();
}

button.addEventListener("click", toggle);
