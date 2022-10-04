import {Box, CircularProgress} from "@mui/material";

export default function FeedSkeleton() {
    return (
        <Box sx={{display: 'flex'}}>
            <CircularProgress size={'3rem'}/>
        </Box>
    );
}
