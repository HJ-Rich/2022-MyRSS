import {useEffect, useState} from "react";
import axios from "axios";
import LoadingSpinner from "../views/LoadingSpinner";
import RssComponent from "./RssComponent";
import {Button, TextField} from "@mui/material";
import {NavLink} from "react-router-dom";

export default function Rss() {
    const [rss, setRss] = useState([]);
    const [init, setInit] = useState(true);

    useEffect(() => {
        axios.get(`${process.env.REACT_APP_API_HOST}/api/rss`,
            {withCredentials: true})
            .then(({data}) => {
                setRss(data.rssResponses)
                setInit(false);

                if (data.rssResponses.length !== 0) {
                    document.getElementById('bottomIdentifier').style.display = 'inherit';
                }
            })
            .catch(error => console.log(error))
    }, []);

    const handleSubscribe = (e) => {
        const requestUrl = e.target.previousSibling.querySelector('input').value;
        axios.post(`${process.env.REACT_APP_API_HOST}/api/subscribes`,
            {url: requestUrl},
            {withCredentials: true})
            .then(({data}) => {
                location.reload();
            })
            .catch(error => console.log(error))
    }

    return (
        <>
            <NavLink to='/howTo' style={{color: 'inherit'}}> RSS 등록 방법이 궁금하신가요? 💁‍ </NavLink>
            <div style={{
                display: 'flex',
                flexDirection: 'row',
                width: '90%',
                marginTop: 20,
                marginBottom: 20,
                justifyContent: 'center'
            }}>
                <TextField
                    id="outlined-basic"
                    label="구독할 RSS를 입력해주세요"
                    variant="outlined"
                    style={{width: "100%", maxWidth: 500}}
                />
                <Button onClick={handleSubscribe} style={{marginLeft: 10}} variant="contained">Add</Button>
            </div>
            {
                init ? <LoadingSpinner/>
                    :
                    rss.length === 0 ?
                        <div>
                            <div>첫 RSS를 추가해볼까요? 😃</div>
                            <div>&emsp;</div>
                        </div>
                        :
                        rss.map(r =>
                            <RssComponent
                                key={r.id}
                                id={r.id}
                                title={r.title}
                                rssUrl={r.rssUrl}
                                link={r.link}
                                iconUrl={r.iconUrl}
                                rssList={rss}
                                rssFunction={setRss}
                            ></RssComponent>
                        )
            }

            <div id="bottomIdentifier" style={{display: 'none', height: 200}}>모두 불러왔어요 🙌</div>
        </>
    );
}
