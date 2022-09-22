import Feed from "./Feed";
import {useEffect, useState} from "react";
import axios from "axios";

export default function DefaultFeeds() {
    const feeds = useFeeds();

    return (
        <>
            {
                feeds.map(feed =>
                    <Feed key={feed.id}
                          id={feed.id}
                          title={feed.title}
                          link={feed.link}
                          updated={feed.updateDate}>
                    </Feed>
                )
            }
        </>
    );
}


const useFeeds = () => {
    const [feeds, setFeeds] = useState([]);

    useEffect(() => {
        axios.get(`${process.env.REACT_APP_API_HOST}/api/feeds`)
            .then(res => setFeeds(res.data.feedResponses))
            .catch(err => console.log(err));
    }, []);

    return feeds;
}
