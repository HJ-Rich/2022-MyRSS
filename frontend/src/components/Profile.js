import {Button, TextField} from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";
import axios from "axios";

export default function Profile(props) {
    function logoutHandler() {
        axios.post(`/api/auth/invalidate`)
            .then(({data}) => window.location.href = '/')
    }

    return (
        <>
            <div><img src={props.userInfo.imageUrl} style={{maxWidth: '100px', borderRadius: '50%'}}/></div>
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
        </>
    )
}
