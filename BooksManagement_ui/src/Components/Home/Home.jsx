import React from "react";
import "../Styles/Home.css";


const Home = () => {
  return (
    <>
    {/* <AppNavbar /> */}
      <div className="home-container">
      <div className="hero-section">
        <h1 className="hero-title">
          Welcome to Stay Focus
        </h1>

        <p className="hero-description">
          Discover books, authors, and knowledge that
          inspire your journey.
        </p>

        <button className="hero-button">
          Explore Books
        </button>
      </div>
    </div>
   </> 
  );
};

export default Home;