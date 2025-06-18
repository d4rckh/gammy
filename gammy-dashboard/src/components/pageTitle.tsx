export default function PageTitle({title, description}: { title: string, description?: string }) {
    return <>
        <h1 className="text-3xl my-4">{title}</h1>
        {
            description && <div className="text-muted-foreground mt-2 mb-4">{description}</div>
        }
    </>
}