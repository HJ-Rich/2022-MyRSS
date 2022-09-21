import {Card, CardActionArea, CardContent, Typography} from "@mui/material";

function Feed(feed) {
    return (
        <Card sx={{width: 700, height: 60, marginLeft: 3, marginTop: 1, marginBottom: 3}}>
            <CardActionArea>
                {/*<CardMedia component="img" image={DefaultRoadmapImage} sx={{height: 200, width: 300}}/>*/}
                <CardContent>
                    <Typography gutterBottom variant="h6" component="div">{feed.title}</Typography>
                    <Typography variant="h6" component="div">{feed.link}</Typography>
                    <Typography variant="h6" component="div">{feed.updated}</Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}

export default Feed;
