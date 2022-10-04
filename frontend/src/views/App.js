import '../css/App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import Home from "./Home";
import OAuthGithubCallback from "./OAuthGithubCallback";
import Subscribed from "./Subscribed";
import {useEffect, useState} from "react";
import axios from "axios";
import GithubLogin from "./GithubLogin";
import Profile from "./Profile";
import Search from "./Search";
import Bookmarks from "./Bookmarks";

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
                                       fetchOption={''}
                                       navIndex={0}/>
                               }>
                        </Route>
                        <Route path="/search" exact
                               element={
                                   <Search
                                       loginStatus={loginStatus}
                                       userInfo={userInfo}
                                       fetchOption={''}
                                       navIndex={1}/>
                               }>
                        </Route>
                        <Route path="/subscribed" exact
                               element={
                                   loginStatus
                                       ? <Subscribed loginStatus={loginStatus}
                                                     userInfo={userInfo}
                                                     fetchOption={'&subscribed=true'}
                                                     navIndex={2}/>
                                       : <GithubLogin loginStatus={loginStatus} navIndex={4}/>
                               }>
                        </Route>
                        <Route path="/bookmarks" exact
                               element={
                                   <Bookmarks
                                       loginStatus={loginStatus}
                                       userInfo={userInfo}
                                       fetchOption={''}
                                       navIndex={3}/>
                               }>
                        </Route>
                        <Route path="/profile" exact
                               element={
                                   loginStatus
                                       ? <Profile loginStatus={loginStatus}
                                                  userInfo={userInfo}
                                                  fetchOption={'&subscribed=true'}
                                                  navIndex={4}/>
                                       : <GithubLogin loginStatus={loginStatus} navIndex={4}/>
                               }
                        >
                        </Route>
                        <Route path="/oauth/github" exact
                               element={<OAuthGithubCallback/>}></Route>
                    </Routes>
                </BrowserRouter>
            </ThemeProvider>
        </>
    );
}

export default App;
