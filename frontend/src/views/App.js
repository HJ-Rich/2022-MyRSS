import '../css/App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import Home from "./Home";
import OAuthGithubCallback from "./OAuthGithubCallback";
import Home2 from "./Home2";

const darkTheme = createTheme({
    palette: {
        mode: "dark",
    },
});

function App() {
    return (
        <>
            <ThemeProvider theme={darkTheme}>
                <CssBaseline/>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" exact element={
                            <Home fetchOption={''} navIndex={0}/>}>
                        </Route>
                        <Route path="/subscribed" exact element={
                            <Home2 fetchOption={'&subscribed=true'} navIndex={2}/>}>
                        </Route>
                        <Route path="/oauth/github" exact element={<OAuthGithubCallback/>}></Route>
                    </Routes>
                </BrowserRouter>
            </ThemeProvider>
        </>
    );
}

export default App;
