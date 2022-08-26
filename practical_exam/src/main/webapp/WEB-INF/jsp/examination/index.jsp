<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모의 시험</title>

<link href="css/shamExamStyle.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.7/css/swiper.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.7/js/swiper.min.js"></script>
<script type="text/javascript" src="js/common/ajax.js"></script>

<script>


var interleaveOffset = 0.5;

var swiperOptions = {
  loop: true,
  speed: 1000,
  grabCursor: true,
  watchSlidesProgress: true,
  mousewheelControl: true,
  keyboardControl: true,
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev"
  },
  on: {
    progress: function() {
      var swiper = this;
      for (var i = 0; i < swiper.slides.length; i++) {
        var slideProgress = swiper.slides[i].progress;
        var innerOffset = swiper.width * interleaveOffset;
        var innerTranslate = slideProgress * innerOffset;
        swiper.slides[i].querySelector(".slide-inner").style.transform =
          "translate3d(" + innerTranslate + "px, 0, 0)";
      }      
    },
    touchStart: function() {
      var swiper = this;
      for (var i = 0; i < swiper.slides.length; i++) {
        swiper.slides[i].style.transition = "";
      }
    },
    setTransition: function(speed) {
      var swiper = this;
      for (var i = 0; i < swiper.slides.length; i++) {
        swiper.slides[i].style.transition = speed + "ms";
        swiper.slides[i].querySelector(".slide-inner").style.transition =
          speed + "ms";
      }
    }
  }
};

var swiper = new Swiper(".swiper-container", swiperOptions);

// document.querySelector('[data-toggle]').addEventListener('click', function(){
//   if (swiper.realIndex == 0) {
//     swiper.slideTo(swiper.slides.length - 1);
//   } else {
//     swiper.slideTo(0);
//   }
// });

// function logIndex () {
//   requestAnimationFrame(logIndex);
//   console.log(swiper.realIndex);
// }
// logIndex();

</script>

</head>
<body>

	<div class="swiper-container">
		<div class="swiper-wrapper">
			<!--     <div class="swiper-slide">
      <div class="slide-inner" style="background-image:url(http://cs412624.vk.me/v412624691/4117/RWBNZL6CLtU.jpg)"></div>
    </div>
    <div class="swiper-slide">
      <div class="slide-inner" style="background-image:url(http://cs412624.vk.me/v412624691/41ad/atM6w55Z9Xg.jpg)"></div>
    </div> -->
			<div class="swiper-slide">
				<div class="slide-inner"
					style="background-image: url(https://pp.userapi.com/c836139/v836139003/63ce1/QThPx7qPzvU.jpg)"></div>
			</div>
			<div class="swiper-slide">
				<div class="slide-inner"
					style="background-image: url(https://pp.userapi.com/c412624/v412624691/4136/_da_uAA6ha8.jpg)"></div>
			</div>
			<div class="swiper-slide">
				<div class="slide-inner"
					style="background-image: url(https://pp.userapi.com/c637331/v637331691/48f5f/spHnV42iYVw.jpg)"></div>
			</div>
			<div class="swiper-slide">
				<div class="slide-inner"
					style="background-image: url(https://pp.userapi.com/c837139/v837139407/67f52/fFqjq4U2mEk.jpg)"></div>
			</div>
			<!--     <div class="swiper-slide">
      <div class="slide-inner" style="background-image:url(http://cs412624.vk.me/v412624691/415d/X7YuVilSl4k.jpg)"></div>
    </div> -->
		</div>
		<div class="swiper-button-next swiper-button-white"></div>
		<div class="swiper-button-prev swiper-button-white"></div>
	</div>
	
	<div class="copy">
		Photos by <a target="_blank" href="https://500px.com/udovichenko">Dmitry
			Udovichenko</a>
		<button data-toggle>toggle</button>
	</div>



</body>
</html>