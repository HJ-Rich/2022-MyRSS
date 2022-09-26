import '../css/App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Home from "./Home";
import OAuthGithubCallback from "./OAuthGithubCallback";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";

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
                        <Route path="/" exact element={<Home/>}></Route>
                        <Route path="/oauth/github" exact element={<OAuthGithubCallback/>}></Route>
                    </Routes>
                </BrowserRouter>
            </ThemeProvider>
        </>
    );
}

export default App;
