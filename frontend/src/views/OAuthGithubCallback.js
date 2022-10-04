import {useSearchParams} from "react-router-dom";
import axios from "axios";
import {Box, CircularProgress} from "@mui/material";

export default function OAuthGithubCallback() {
    const [searchParams] = useSearchParams();
    const code = searchParams.get("code");

    axios.post(`${process.env.REACT_APP_API_HOST}/api/auth/login`,
        {
            code: code
        },
        {
            headers: {"Content-Type": "application/json"},
            withCredentials: true
        }
    ).then(({data}) => {
        window.location.href = '/'
    });

    return (
        <div className="App">
            <header className="App-header">
                <Box sx={{display: 'flex'}}>
                    <CircularProgress size={'3rem'}/>
                </Box>
            </header>
        </div>
    );
}
