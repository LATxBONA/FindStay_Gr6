document.addEventListener("DOMContentLoaded", function() {

	var register = document.getElementById("register");
	var login = document.getElementById("login");
	var logout = document.getElementById("logout");
	var recharge = document.getElementById("recharge");

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

	console.log(header_fullname)
	
	if (header_fullname) {
		headerAccountMenus.classList.add('active_header');
	}
})