import React, { useEffect } from "react";
import LoginForm from "../components/loginForm";
import RegistroForm from "../components/registroForm";

function Login() {
	useEffect(() => {
		const signUpButton = document.getElementById("signUp");
		const signInButton = document.getElementById("signIn");
		const container = document.getElementById("container");

		signUpButton?.addEventListener("click", () => {
			container?.classList.add("right-panel-active");
		});

		signInButton?.addEventListener("click", () => {
			container?.classList.remove("right-panel-active");
		});
	});

	return (
		<div className="pagina-login">
			<div className="container" id="container">
				<div className="form-container sign-up-container">
					<RegistroForm />
				</div>
				<div className="form-container sign-in-container">
					<LoginForm />
				</div>
				<div className="overlay-container">
					<div className="overlay">
						<div className="overlay-panel overlay-left">
							<h1>Bem vindo de volta!</h1>
							<p>Para permanecer conectado, por favor acesse com suas informações</p>
							<button className="ghost" id="signIn">
								Entrar
							</button>
						</div>
						<div className="overlay-panel overlay-right">
							<h1>Olá!</h1>
							<p>Informe seus dados e começe sua jornada</p>
							<button className="ghost" id="signUp">
								Registrar
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}

export default Login;
