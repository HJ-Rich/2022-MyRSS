import ShareIcon from '@mui/icons-material/Share';
import BookmarkAddIcon from '@mui/icons-material/BookmarkAdd';
import BookmarkAddedIcon from '@mui/icons-material/BookmarkAdded';

import {Alert, Card, CardActions, CardContent, CardHeader, IconButton, Link, Snackbar, Typography} from "@mui/material";
import axios from "axios";
import {useState} from "react";

export default function Feed(feed) {
    const date = new Date(feed.updateDate);
    const formatDate = new Intl.DateTimeFormat('kr', {dateStyle: 'medium', timeStyle: 'short'}).format(date);
    const [bookmark, setBookmark] = useState(feed.bookmarked === undefined ? false : feed.bookmarked);

    const [open, setOpen] = useState(false);

    const handleClose = () => {
        setOpen(false);
    };

    const handleBookmark = () => {
        axios.post(`${process.env.REACT_APP_API_HOST}/api/bookmarks`,
            {id: feed.id}, {withCredentials: true})
            .then(({data}) => {
                setOpen(true);
                setBookmark(true);
            })
    }

    return (
        <>
            <Snackbar anchorOrigin={{vertical: 'top', horizontal: 'center'}} open={open} autoHideDuration={800}
                      onClose={() => setOpen(false)}>
                <Alert onClose={handleClose} severity={'success'} sx={{width: '100%'}}>
                    북마크에 추가했어요 😃
                </Alert>
            </Snackbar>
            <Card sx={{maxWidth: 500, marginTop: 5, marginBottom: 5}}>
                <Link href={feed.link} target='_blank' color={'inherit'} underline={'none'}>
                    <CardHeader
                        title={feed.title}
                        titleTypographyProps={{variant: 'h6'}}
                        style={{
                            textAlign: 'center', paddingLeft: 30, paddingRight: 30
                        }}
                    />
                    <CardContent>
                        <Typography variant="body2" color="text.secondary" style={{textAlign: 'right'}}>
                            {formatDate}
                        </Typography>
                    </CardContent>
                    <CardContent>
                        <Typography variant="body2" color="text.secondary"
                                    style={{maxWidth: 500, lineBreak: 'anywhere'}}>
                            {feed.description}
                        </Typography>
                    </CardContent>
                </Link>
                <CardActions disableSpacing sx={{display: 'flex', justifyContent: 'flex-end'}}>
                    <img src={feed.iconUrl} width={25}/>
                    <div style={{fontSize: '1.1rem', padding: 4, marginLeft: 5}}>{feed.rssTitle}</div>
                    <div style={{marginLeft: 10}}></div>
                    <IconButton
                        aria-label="Like"
                        onClick={handleBookmark}
                    >{
                        (bookmark === true) ? <BookmarkAddedIcon/> : <BookmarkAddIcon/>
                    }
                    </IconButton>

                    <IconButton
                        aria-label="share"
                        onClick={() => {
                            if (navigator.share) {
                                navigator.share({
                                    title: feed.title,
                                    url: feed.link,
                                    text: feed.description
                                })
                            } else {
                                navigator.clipboard.writeText(feed.link).then(result => alert('클립보드에 복사했어요~!'));
                            }
                        }}>
                        <ShareIcon/>
                    </IconButton>
                </CardActions>
            </Card>
        </>
    );
}
