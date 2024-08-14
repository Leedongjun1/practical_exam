var interleaveOffset = 0.5;

var swiperOptions = {
	speed: 300,
	watchSlidesProgress: true,
	touchRatio: 0,
	keyboardControl: true,
	navigation: {
		nextEl: ".swiper-button-next",
		prevEl: ".swiper-button-prev"
	},
	pagination: {
		el: '.swiper-pagination',
		clickable: true,


		renderBullet: function(index, className) {
			return '<div class="' + className + '"><span>' + (index + 1 + "번") + '</span></div>';
		}
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