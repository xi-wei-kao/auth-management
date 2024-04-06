export type LoginActionReq = {
    'username': string,
    'password': string
}

export type GetOverviewReq = {
    'type': 'month' | 'week' | 'day'
}