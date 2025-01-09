document.addEventListener("DOMContentLoaded", function() {

	var register = document.getElementById("register");
	var login = document.getElementById("login");
	var logout = document.getElementById("logout");
	var recharge = document.getElementById("recharge");
	var changepass = document.getElementById("change_password");
	if (login && register) {
		login.addEventListener("click", function() {
			window.location.href = "/login";
		})

		register.addEventListener("click", function() {
			window.location.href = "/register";
		})
	}

	if (logout) {
		logout.addEventListener("click", function() {
			window.location.href = "/logout";
		})
	}

	if (changepass) {
		changepass.addEventListener("click", function() {
			window.location.href = "/info";
		})
	}
	if (recharge) {
		recharge.addEventListener("click", function() {
			window.location.href = "/recharge";
		})
	}

	var header_fullname = document.getElementById("header_fullname");
	var header_popup = document.getElementById("header_popup");
	var popup = document.getElementById("popup");

	var favorite = document.querySelector('.header-heart');

	favorite.addEventListener('click', function() {
		if (!header_fullname) {
			redirectFavoritepage();
		}
	});


	function redirectFavoritepage() {
		header_popup.classList.add("show_header_popup");
		popup.classList.add("show_popup");
	}

	header_popup.addEventListener("click", function(e) {
		header_popup.classList.remove("show_header_popup");
		popup.classList.remove("show_popup");
	})

	popup.addEventListener("click", function(e) {
		e.stopPropagation();
	});

	var headerAccountMenus = document.getElementById('header-account-menu');
	var listItems = headerAccountMenus.getElementsByTagName('li');
	var screenWidth = screen.width;
	var screenHeight = screen.height;
	if (listItems.length == 3) {
		if (screenWidth == 1536 && screenHeight == 864) {
			headerAccountMenus.classList.add('active_header_1');
		} else {
			headerAccountMenus.classList.add('active_header_2');
		}
	} else if (listItems.length == 2) {
		if (screenWidth == 1536 && screenHeight == 864) {
			headerAccountMenus.classList.add('active_header_3');
		} else {
			headerAccountMenus.classList.add('active_header_4');
		}
	}


	var info = document.getElementById("infor_detail_user");
	if (info) {
		info.addEventListener("click", function() {
			window.location.href = "/info";
		});
	}
})