import '../css/App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import Home from "./Home";
import OAuthGithubCallback from "./OAuthGithubCallback";
import Subscribed from "./Subscribed";
import {useEffect, useState} from "react";
import axios from "axios";
import GithubLogin from "./GithubLogin";
import Menu from "./Menu";
import Social from "./Social";
import Bookmarks from "./Bookmarks";
import ManageRss from "./ManageRss";

const darkTheme = createTheme({
    palette: {
        mode: "dark",
    },
});

function App() {
    const [loginStatus, setLoginStatus] = useState();
    const [userInfo, setUserInfo] = useState({});

    useEffect(() => {
        axios.post(`${process.env.REACT_APP_API_HOST}/api/auth/certificate`,
            {}, {withCredentials: true})
            .then(({data}) => {
                setLoginStatus(data.loggedIn);
                setUserInfo(data.member);
            })
    }, [])

    return (
        <>
            <ThemeProvider theme={darkTheme}>
                <CssBaseline/>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" exact
                               element={
                                   <Home
                                       loginStatus={loginStatus}
                                       userInfo={userInfo}
                                       navIndex={0}/>
                               }>
                        </Route>
                        <Route path="/social" exact
                               element={
                                   <Social
                                       loginStatus={loginStatus}
                                       userInfo={userInfo}
                                       navIndex={1}/>
                               }>
                        </Route>
                        <Route path="/subscribed" exact
                               element={
                                   loginStatus
                                       ? <Subscribed
                                           loginStatus={loginStatus}
                                           userInfo={userInfo}
                                           navIndex={2}/>
                                       : <GithubLogin needAlert={true} loginStatus={loginStatus} navIndex={4}/>
                               }>
                        </Route>
                        <Route path="/bookmarks" exact
                               element={
                                   loginStatus ?
                                       <Bookmarks
                                           loginStatus={loginStatus}
                                           userInfo={userInfo}
                                           navIndex={3}/>
                                       : <GithubLogin needAlert={true} loginStatus={loginStatus} navIndex={4}/>
                               }>
                        </Route>
                        <Route path="/menu" exact
                               element={
                                   loginStatus
                                       ? <Menu
                                           loginStatus={loginStatus}
                                           userInfo={userInfo}
                                           navIndex={4}/>
                                       : <GithubLogin loginStatus={loginStatus} navIndex={4}/>
                               }
                        >
                        </Route>

                        <Route path="/manage-rss" exact
                               element={
                                   loginStatus
                                       ? <ManageRss
                                           loginStatus={loginStatus}
                                           userInfo={userInfo}
                                           navIndex={4}/>
                                       : <GithubLogin loginStatus={loginStatus} navIndex={4}/>
                               }
                        >
                        </Route>

                        <Route path="/oauth/github" exact
                               element={<OAuthGithubCallback/>}/>
                    </Routes>
                </BrowserRouter>
            </ThemeProvider>
        </>
    );
}

export default App;
