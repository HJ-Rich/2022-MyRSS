import BottomNavBar from "../components/BottomNavBar";
import axios from "axios";
import {Button, TextField} from "@mui/material";
import LogoutIcon from '@mui/icons-material/Logout';

export default function Profile(props) {
    function logoutHandler() {
        axios.post(`${process.env.REACT_APP_API_HOST}/api/auth/invalidate`,
            {}, {withCredentials: true})
            .then(({data}) => window.location.href = '/')
    }

    return (
        <div className="App">
            <header className="App-header">
                <div>
                    <div><img src={props.userInfo.imageUrl} style={{maxWidth: '100px', borderRadius: '50%'}}/></div>
                    <div>
                        <TextField
                            disabled
                            id="filled-disabled"
                            label="Id"
                            defaultValue={props.userInfo.id}
                            variant="filled"
                        />
                    </div>
                    <div>
                        <TextField
                            disabled
                            id="filled-disabled"
                            label="Nickname"
                            defaultValue={props.userInfo.nickname}
                            variant="filled"
                        />
                    </div>
                    <div style={{marginTop: '30px'}}>
                        <Button variant="outlined" color="error" onClick={logoutHandler}>
                            <span style={{marginRight: '10px'}}>Sign out</span> <LogoutIcon/>
                        </Button>
                    </div>
                </div>
            </header>
            <footer className="App-footer">
                <BottomNavBar loginStatus={props.loginStatus} userInfo={props.userInfo} navIndex={props.navIndex}/>
            </footer>
        </div>
    )
}
