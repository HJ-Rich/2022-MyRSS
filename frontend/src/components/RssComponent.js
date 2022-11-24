import {Button, Card, CardContent, Typography} from "@mui/material";
import DeleteIcon from '@mui/icons-material/Delete';
import {useEffect} from "react";
import axios from "axios";

export default function RssComponent(props) {
    const handleDelete = () => {
        axios.delete(
            `/api/rss?id=${props.id}`)
            .then(({data}) => {
                location.reload()
            }).catch(error => console.log(error))
    }

    useEffect(() => {
        axios.get(`/api/rss`)
            .then(({data}) => {
                setRss(data.rssResponses)
                setInit(false);
            })
            .catch(error => console.log(error))
    }, []);

    return (
        <Card style={{
            minWidth: 350,
            width: '50%',
            maxWidth: '80%',
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'space-between',
            marginBottom: 30
        }}>
            <CardContent style={{width: '100%'}}>
                <img src={props.iconUrl} style={{maxWidth: '50px', borderRadius: '50%'}}/>
                <Typography component="div" variant="h5">
                    {props.title}
                </Typography>
                <Typography variant="subtitle1" color="text.secondary" component="div">
                    <Button onClick={handleDelete} variant="outlined"
                            color="inherit"><DeleteIcon/> &emsp;Unsubscribe</Button>
                </Typography>
            </CardContent>
        </Card>
    );
}
