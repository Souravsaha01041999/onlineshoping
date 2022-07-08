<?php
$f=fopen("ctrldata.txt",'r');
$s1=fread($f,filesize("ctrldata.txt"));
fclose($f);
$sett="";
if($s1=="OFF")
{
    $sett="unchecked";
}
else
{
    $sett="checked"; 
}
?>
<html>
<head><title>CONTROL</title></head>
<body>
<center>
<style>
*, *:before, *:after {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}
:root {
	/* minFontSize + (maxFontSize - minFontSize) * (100vw - minVWidth)/(maxVWidth - minVWidth) */
	font-size: calc(64px + (80 - 64) * (100vw - 320px)/(960 - 320));
}
body, input {
	font-size: 1em;
	line-height: 1.5;
}
input {
	display: block;
	margin-bottom: 1.5em;
}
main {
	padding: 1.5em 0 0 0;
	text-align: center;	
}
.l {
	background-color: rgba(0,0,0,0.7);
	border-radius: 0.75em;
	box-shadow: 0.125em 0.125em 0 0.125em rgba(0,0,0,0.3) inset;
	color: #fdea7b;
	display: inline-flex;
	align-items: center;
	margin: auto;
	padding: 0.15em;
	width: 3em;
	height: 1.5em;
	transition: background-color 0.1s 0.3s ease-out, box-shadow 0.1s 0.3s ease-out;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
}
.l:before, .l:after {
	content: "";
	display: block;
}
.l:before {
	background-color: #d7d7d7;
	border-radius: 50%;
	width: 1.2em;
	height: 1.2em;
	transition: background-color 0.1s 0.3s ease-out, transform 0.3s ease-out;
	z-index: 1;
}
.l:after {
	background:
		linear-gradient(transparent 50%, rgba(0,0,0,0.15) 0) 0 50% / 50% 100%,
		repeating-linear-gradient(90deg,#bbb 0,#bbb,#bbb 20%,#999 20%,#999 40%) 0 50% / 50% 100%,
		radial-gradient(circle at 50% 50%,#888 25%, transparent 26%);
	background-repeat: no-repeat;
	border: 0.25em solid transparent;
	border-left: 0.4em solid #d8d8d8;
	border-right: 0 solid transparent;
	transition: border-left-color 0.1s 0.3s ease-out, transform 0.3s ease-out;
	transform: translateX(-22.5%);
	transform-origin: 25% 50%;
	width: 1.2em;
	height: 1em;
}
/* Checked */
.l:checked {
	background-color: rgba(0,0,0,0.45);
	box-shadow: 0.125em 0.125em 0 0.125em rgba(0,0,0,0.1) inset;
}
.l:checked:before {
	background-color: currentColor;
	transform: translateX(125%)
}
.l:checked:after {
	border-left-color: currentColor;
	transform: translateX(-2.5%) rotateY(180deg);
}
/* Other States */
.l:focus {
	/* Usually an anti-A11Y practice but set to remove an annoyance just for this demo */
	outline: 0;
}
</style>
<main>
    OFF 
  <input class="l" type="checkbox" onchange="cng()" <?php echo $sett; ?> id="sw"> ON
</main>
<script>
function cng()
{
    var snd;
    var da=document.getElementById("sw").checked;
    if(da==true)
    {
        snd="ON";
    }
    else
    {
        snd="OFF";
    }
                
                var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      
    }
  };
  xhttp.open("GET", "setcontrol.php?d="+snd, true);
  xhttp.send();
}
</script>
</center>
</body>
</html>