import DefaultFeeds from "./DefaultFeeds";
import BottomNavBar from "../components/BottomNavBar";
import AddIcon from '@mui/icons-material/Add';
import {Box, Button, Fab, Modal, TextField, Typography} from "@mui/material";
import {useState} from "react";

export default function Subscribed(props) {
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const httpRegex = '^(https|http):\\/\\/[^\\s$.?#].[^\\s]*$';

    const handleSubscribe = (e) => {

        if (input.length === 0) {
            return;
        }

        // axios.post(`${process.env.REACT_APP_API_HOST}/api/subscribes`,
        //     {url: input}, {withCredentials: true})
        //     .then(({data}) => {
        //         console.log(data);
        //
        //         location.reload();
        //     });
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
        <div className="App">
            <header className="App-header">
                <Fab size='small' color="primary" aria-label="add" style={{position: 'fixed', top: '85vh'}}>
                    <AddIcon onClick={handleOpen}/>
                </Fab>
                <DefaultFeeds fetchOption={props.fetchOption}/>

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
            </header>
            <footer className="App-footer">
                <BottomNavBar loginStatus={props.loginStatus} userInfo={props.userInfo} navIndex={props.navIndex}/>
            </footer>
        </div>
    );
}
