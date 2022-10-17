import {Alert, Snackbar} from "@mui/material";
import {useState} from "react";

export default function UnauthorizedMessage(props) {
    const [open, setOpen] = useState(true);

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <Snackbar anchorOrigin={{vertical: 'top', horizontal: 'center'}} open={open} autoHideDuration={800}
                  onClose={() => setOpen(false)}>
            <Alert onClose={handleClose} severity={'error'} sx={{width: '100%'}}>
                로그인이 필요한 기능이에요 😃
            </Alert>
        </Snackbar>
    );
}
