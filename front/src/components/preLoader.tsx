import React, { useState, useEffect } from "react";
import "../assets/css/loader.scss";

export default function Preloader() {
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		window.addEventListener("loadstart", () => {
			setLoading(true);
			console.log("iniciou loadstart");
		});
		window.addEventListener("load", () => {
			setLoading(false);
			console.log("terminou load");
		});
	}, []);

	return (
		<>
			{loading && (
				<div className="wrap">
					<div className="loading">
						<div className="bounceball"></div>
						<div className="text">NOW LOADING</div>
					</div>
				</div>
			)}
		</>
	);
}
