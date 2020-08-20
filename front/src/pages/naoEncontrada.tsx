import React, { useEffect } from "react";
import "../assets/css/notFoundPage.scss";

export default function NaoEncontrada() {
	useEffect(() => {
		const randomNum = (): string => {
			return (Math.floor(Math.random() * 9) + 1).toString();
		};

		let i: number = 0;
		const time: number = 30;

		const selector3 = document.querySelector(".thirdDigit");
		const selector2 = document.querySelector(".secondDigit");
		const selector1 = document.querySelector(".firstDigit");
		const loop3 = setInterval(function () {
			if (!selector3) return;
			if (i > 40) {
				clearInterval(loop3);
				selector3.textContent = "4";
			} else {
				selector3.textContent = randomNum();
				i++;
			}
		}, time);

		const loop2 = setInterval(function () {
			if (!selector2) return;
			if (i > 80) {
				clearInterval(loop2);
				selector2.textContent = "0";
			} else {
				selector2.textContent = randomNum();
				i++;
			}
		}, time);

		const loop1 = setInterval(function () {
			if (!selector1) return;
			if (i > 100) {
				clearInterval(loop1);
				selector1.textContent = "4";
			} else {
				selector1.textContent = randomNum();
				i++;
			}
		}, time);
	});

	return (
		<div className="error">
			<div className="container-floud">
				<div className="col-xs-12 ground-color text-center">
					<div className="container-error-404">
						<div className="clip">
							<div className="shadow">
								<span className="digit thirdDigit"></span>
							</div>
						</div>
						<div className="clip">
							<div className="shadow">
								<span className="digit secondDigit"></span>
							</div>
						</div>
						<div className="clip">
							<div className="shadow">
								<span className="digit firstDigit"></span>
							</div>
						</div>
						<div className="msg">
							OH!<span className="triangle"></span>
						</div>
					</div>
					<h2 className="h1">Desculpe! Página não encontrada</h2>
				</div>
			</div>
		</div>
	);
}
