document.addEventListener("DOMContentLoaded", function() {
	var image_main = document.getElementById('image_main');
	var list_image = document.querySelectorAll('.imageRoom img');

	var preBtn = document.getElementById('pre_image');
	var nextBtn = document.getElementById('next_image');

	var index = 0;

	function updateImage(index) {
		setTimeout(function() {
			image_main.src = list_image[index].getAttribute("src");
		}, 300)
	}

	function animationImage(direction) {
		image_main.style.animation = '';
		let arrayAnimation = ['slideCenterToLeftPre', 'slideRightToCenterPre', 'slideCenterToRightNext', 'slideLeftToRightNext'];

		var animationName = " .5s ease forwards";

		if (direction == "left") {
			image_main.style.animation = arrayAnimation[0] + animationName;
		} else if (direction == "right") {
			image_main.style.animation = arrayAnimation[2] + animationName;
		}

		setTimeout(function() {
			if (direction == "left") {
				image_main.style.animation = arrayAnimation[1] + animationName;
			} else {
				image_main.style.animation = arrayAnimation[3] + animationName;
			}
		}, 100)
	}

	preBtn.addEventListener("click", function() {
		if (index == 0) {
			index = list_image.length - 1
		} else {
			index--;
		}

		updateImage(index);
		animationImage("left");
	})

	nextBtn.addEventListener("click", function() {
		if (index == list_image.length - 1) {
			index = 0
		} else {
			index++;
		}

		updateImage(index);
		animationImage("right");
	})

	updateImage(index);
})