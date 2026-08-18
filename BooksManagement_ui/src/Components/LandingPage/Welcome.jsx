import { useNavigate } from "react-router-dom";
import "../Styles/Welcome.css";

function Welcome() {

    const navigate = useNavigate();

    const username = sessionStorage.getItem("userName");
console.log(username,"UserName", localStorage)
    const handleContinue = () => {
        navigate("/home");
                        sessionStorage.removeItem("token");
                        sessionStorage.removeItem("userName");
                        sessionStorage.removeItem("isLoggedIn");
    };

    return (
        <div className="welcome-container">
            <div className="welcome-card">

                <h1>
                    Welcome {username} 
                </h1>

                <p>
                    Login Successful
                </p>

                {/* <button
                    className="continue-btn"
                    onClick={handleContinue}
                >
                    Continue
                </button> */}

            </div>
        </div>
    );
}

export default Welcome;