import {useState} from "react";
import axios from "axios";
import {Alert, Box, Button, Fab, Modal, Snackbar, TextField, Typography} from "@mui/material";
import AddIcon from '@mui/icons-material/Add';

export default function SubscribeModal() {
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const [openSuccess, setOpenSuccess] = useState(false);
    const [openError, setOpenError] = useState(false);


    const handleSubscribe = (e) => {
        const input = document.getElementById('outlined-basic').value;
        if (input.length === 0) {
            return;
        }

        axios.post(`${process.env.REACT_APP_API_HOST}/api/subscribes`,
            {url: input}, {withCredentials: true})
            .then(({data}) => {
                handleClose()
                setOpenSuccess(true)
                setTimeout(() => location.reload(), 500)
            }).catch(error => setOpenError(true));
    }

    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
    };

    return (
        <>
            <Fab size='small' color="primary" aria-label="add" style={{position: 'fixed', top: '85vh'}}>
                <AddIcon onClick={handleOpen}/>
            </Fab>
            <Snackbar
                anchorOrigin={{vertical: 'top', horizontal: 'center'}} open={openSuccess}
                autoHideDuration={800}
                onClose={() => setOpen(false)}>
                <Alert onClose={() => setOpenSuccess(false)} severity={'success'} sx={{width: '100%'}}>
                    요청하신 RSS를 구독했어요 😃
                </Alert>
            </Snackbar>
            <Snackbar anchorOrigin={{vertical: 'top', horizontal: 'center'}} open={openError} autoHideDuration={800}
                      onClose={() => setOpen(false)}>
                <Alert onClose={() => setOpenError(false)} severity={'error'} sx={{width: '100%'}}>
                    요청에 실패했어요.. 다른 주소로 시도해볼까요? 😅
                </Alert>
            </Snackbar>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Typography id="modal-modal-title" variant="h6" component="h2" style={{marginBottom: 30}}>
                        구독할 RSS 주소를 입력해주세요
                    </Typography>
                    <TextField id="outlined-basic" label="RSS 주소 입력" variant="outlined" style={{width: "100%"}}/>
                    <div style={{display: 'flex', flexDirection: 'row-reverse'}}>
                        <Button onClick={handleSubscribe} style={{marginTop: 20}} variant="contained">Add</Button>
                    </div>
                </Box>
            </Modal>
        </>
    )
}
