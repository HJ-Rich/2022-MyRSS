import {useEffect, useState} from "react";
import axios from "axios";
import LoadingSpinner from "./LoadingSpinner";
import Feed from "../components/Feed";
import {NavLink} from "react-router-dom";

export default function SubscribedFeeds() {
    const [feeds, setFeeds] = useState([]);
    let pageNumber = 0;
    let loading = false;
    let hasNext = true;
    const [init, setInit] = useState(true);

    const loadMoreFeeds = (() => {
        axios.get(`${process.env.REACT_APP_API_HOST}/api/subscribes?page=${pageNumber}`,
            {withCredentials: true})
            .then(({data}) => {
                const newFeeds = [];
                data.feedResponses.forEach((feed) => newFeeds.push(feed));
                setFeeds(presentFeeds => [...presentFeeds, ...newFeeds]);
                pageNumber = data.nextPageable.pageNumber;
                hasNext = data.hasNext;
                loading = false;
                setInit(false);

                if (!hasNext) {
                    setTimeout(() => {
                        const aa = document.querySelectorAll('.MuiCard-root')
                        const target = aa[aa.length - 1]
                        console.log(target)
                        target.style.marginBottom = '100px';
                    }, 100)
                }
            })
            .catch(error => {
                console.log(error);
                loading = false;
            })
    });

    const handleScroll = (event) => {
        if (loading || !hasNext) {
            return;
        }

        if (window.innerHeight + event.target.documentElement.scrollTop + 1 >=
            event.target.documentElement.scrollHeight
        ) {
            loading = true;
            loadMoreFeeds();
        }
    }

    useEffect(() => {
        loadMoreFeeds();
        window.addEventListener('scroll', (event) => handleScroll(event))
    }, []);

    return (
        <>
            {
                init ? <LoadingSpinner/>
                    :
                    feeds.length === 0 ?
                        <div>
                            <div>아직 구독하는 RSS가 없어요 😃</div>
                            <div>&emsp;</div>
                            <div><NavLink to={'/rss'} style={{color: 'inherit', fontWeight: 500}}>RSS 추가하러 가기</NavLink>
                            </div>
                        </div>
                        :
                        feeds.map(feed =>
                            <Feed
                                key={feed.id}
                                id={feed.id}
                                title={feed.title}
                                link={feed.link}
                                description={feed.description}
                                subscribed={feed.subscribed}
                                updateDate={feed.updateDate}
                                rssTitle={feed.rss.title}
                                iconUrl={feed.rss.iconUrl}
                                bookmarked={feed.bookmarked}
                            ></Feed>
                        )
            }
        </>
    );
}
