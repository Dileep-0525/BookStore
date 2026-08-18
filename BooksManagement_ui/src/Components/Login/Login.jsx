import { useState } from "react";
import "./Login.css";
import { FcGoogle } from "react-icons/fc";
import { FaFacebookF } from "react-icons/fa";
import { GiBullseye } from "react-icons/gi";
import { login } from "../../Services/loginService";
import { useNavigate } from "react-router-dom";



function Login({setRoleId  }) {

    const navigate = useNavigate();
    const [loginData, setLoginData] = useState({
        username: "",
        password: ""
    });

    const handleChange = (event) => {
        const { name, value } = event.target;

        setLoginData({
            ...loginData,
            [name]: value
        });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        console.log("Login Data:", loginData);
        
        // Call login API here
        try {
            const response = await login(loginData);

            if (response.status === 200) {
               setRoleId(response.data.id);
            //    console.log(response.data.id)
                sessionStorage.setItem("roleId", response.data.id);
                console.log(response);
                sessionStorage.setItem("token", response.data.token);
                sessionStorage.setItem("userName", response.data.email);
                navigate("/dashboard");
            }
            // alert("Login Successful");

        } catch (error) {
            alert(error.message);
        }

    };

    return (
        <>
            {/* <AppNavbar /> */}


            <div className="login-container">
                <div className="login-card">

                    <h2 className="login-title">
                        Welcome to
                    </h2>

                    <p className="login-subtitle">
                        Stay Focus <GiBullseye className="logo-icon" />
                    </p>

                    <form onSubmit={handleSubmit}>

                        <div className="form-group">
                            {/* <label>Username</label> */}
                            <input
                                type="text"
                                name="username"
                                placeholder="Enter username"
                                value={loginData.username}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <div className="form-group">
                            {/* <label>Password</label> */}
                            <input
                                type="password"
                                name="password"
                                placeholder="Enter password"
                                value={loginData.password}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        <button
                            type="submit"
                            className="login-btn"
                        >
                            Login
                        </button>
                        <div className="social-divider">
                            <span>OR</span>
                        </div>

                        <div className="social-icons">
                            <button className="social-icon google-icon">
                                <FcGoogle size={28} />
                            </button>

                            <button className="social-icon facebook-icon">
                                <FaFacebookF size={22} />
                            </button>
                        </div>
                    </form>

                    <p className="register-link">
                        Don't have an account? <a href="#">Register</a>
                    </p>

                </div>
            </div>
        </>
    );
}

export default Login;