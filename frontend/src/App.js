import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Home from "./Home";
import OAuthGithubCallback from "./OAuthGithubCallback";

function App() {
    return (
        <>
            <BrowserRouter>
                <Routes>
                    <Route path="/" exact element={<Home/>}></Route>
                    <Route path="/oauth/github" exact element={<OAuthGithubCallback/>}></Route>
                </Routes>
            </BrowserRouter>
        </>
    );
}

export default App;
