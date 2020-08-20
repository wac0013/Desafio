import React, { useState, useEffect } from "react";

interface LoaderProps {
	display: boolean;
}

export default function Loader(props: LoaderProps) {
	const [zindex, setZIndex] = useState(0);

	function maxZIndex(): number {
		const arrayZindex: number[] = Array.from(document.querySelectorAll("body *"))
			.map((a) => parseFloat(window.getComputedStyle(a).zIndex))
			.filter((a) => !isNaN(a));

		return Math.max(...arrayZindex);
	}

	useEffect(() => {
		setZIndex(maxZIndex() + 1);
	}, [props.display]);

	return (
		<div className="loader-layout" style={{ zIndex: zindex, display: props.display ? "flex" : "none" }}>
			<div className="blocks">
				<div className="block orange"></div>
				<div className="block blue"></div>
			</div>
		</div>
	);
}
